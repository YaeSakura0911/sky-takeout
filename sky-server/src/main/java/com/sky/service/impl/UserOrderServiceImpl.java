package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.PageResult;
import com.sky.service.UserOrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 根据Id查询订单
     *
     * @param id 订单Id
     * @return Result
     */
    @Override
    public OrderVO getById(Long id) {

        // OrdersDTO ordersDTO = new OrdersDTO();

        OrderVO orderVO = new OrderVO();

        // 根据订单Id查询订单
        Orders orders = orderMapper.selectById(id);

        // BeanUtils.copyProperties(orders, ordersDTO);
        BeanUtils.copyProperties(orders, orderVO);

        // 根据订单Id查询订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(id);

        // 设置订单详情
        // ordersDTO.setOrderDetails(orderDetailList);
        orderVO.setOrderDetailList(orderDetailList);

        // return ordersDTO;
        return orderVO;
    }

    /**
     * 查询历史订单
     *
     * @param status 订单状态
     * @param page 当前页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    @Override
    public PageResult getOrderForPage(Integer status, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        Page<Orders> ordersPage = (Page<Orders>) orderMapper.selectByUserIdAndStatus(status);

        // 遍历订单列表
        List<OrderVO> orderVOList = ordersPage.getResult().stream().map(orders -> {

            OrderVO orderVO = new OrderVO();

            BeanUtils.copyProperties(orders, orderVO);

            // 查询订单详情
            List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(orders.getId());

            // 设置订单详情列表
            orderVO.setOrderDetailList(orderDetailList);

            return orderVO;

        }).collect(Collectors.toList());

        return new PageResult(ordersPage.getTotal(), orderVOList);
    }

    /**
     * 催单
     *
     * @param id 订单Id
     */
    @Override
    public void reminderOrder(Long id) {

        // 根据订单Id查询订单
        Orders orders = orderMapper.selectById(id);

        // 如果订单为空
        if (orders == null) {
            // 抛出异常
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 封装信息
        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("orderId", id);
        map.put("content", "订单号：" + orders.getNumber());

        // 通过WebSocket发送信息
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }

    /**
     * 提交订单
     *
     * @param ordersSubmitDTO 提交订单DTO
     * @return OrderSubmitVO 提交订单VO
     */
    @Transactional
    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        Orders orders = new Orders();

        BeanUtils.copyProperties(ordersSubmitDTO, orders);

        // 生成订单号
        String number = UUID.randomUUID().toString();
        // 设置订单号
        orders.setNumber(number);

        // 设置订单状态：1 - 待付款
        orders.setStatus(1);

        // 设置用户Id
        orders.setUserId(BaseContext.getCurrentId());

        // 设置下单时间
        orders.setOrderTime(LocalDateTime.now());

        // 设置结账时间
        // orders.setCheckoutTime(LocalDateTime.now());

        // 设置支付状态
        orders.setPayStatus(0);

        // 根据地址Id查询地址
        AddressBook addressBook = addressBookMapper.selectById(ordersSubmitDTO.getAddressBookId());

        BeanUtils.copyProperties(addressBook, orders);

        // 拼接地址
        String address = addressBook.getProvinceName() + addressBook.getCityName() + addressBook.getDistrictName() + addressBook.getDetail();

        // 设置地址
        orders.setAddress(address);

        // 执行插入订单SQL
        orderMapper.insert(orders);

        // 根据用户Id查询购物车
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByUserId(BaseContext.getCurrentId());

        // 遍历购物车列表
        List<OrderDetail> orderDetailList = shoppingCarts.stream().map(shoppingCart -> {

            OrderDetail orderDetail = new OrderDetail();

            BeanUtils.copyProperties(shoppingCart, orderDetail);

            // 设置订单Id
            orderDetail.setOrderId(orders.getId());

            return orderDetail;

        }).collect(Collectors.toList());

        // 执行插入购物车SQL
        orderDetailMapper.insert(orderDetailList);

        // 执行根据用户Id删除购物车SQL
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());

        return new OrderSubmitVO(orders.getId(), number, orders.getAmount(), orders.getOrderTime());

    }

    /**
     * 重复订单
     *
     * @param id 订单Id;
     */
    @Override
    public void repeatOrder(Long id) {

        // 查询出当前订单Id的订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(id);

        // 遍历订单详情列表
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(orderDetail -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            BeanUtils.copyProperties(orderDetail, shoppingCart);

            // 设置用户Id
            shoppingCart.setUserId(BaseContext.getCurrentId());

            // 设置创建时间
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).collect(Collectors.toList());

        // 执行批量插入购物车SQL
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    /**
     * 支付订单
     *
     * @param ordersPaymentDTO 支付订单DTO
     */
    @Override
    public void paymentOrder(OrdersPaymentDTO ordersPaymentDTO) {

        // Orders orders = new Orders();
        Orders orders = orderMapper.selectByNumber(ordersPaymentDTO.getOrderNumber());

        // 设置订单号
        orders.setNumber(ordersPaymentDTO.getOrderNumber());

        // 设置支付方式
        orders.setPayMethod(ordersPaymentDTO.getPayMethod());

        // 设置订单状态：待接单
        orders.setStatus(Orders.TO_BE_CONFIRMED);

        // 设置结账时间
        orders.setCheckoutTime(LocalDateTime.now());

        // 设置支付状态：已支付
        orders.setPayStatus(Orders.PAID);

        // 执行根据订单号更新订单SQL
        // orderMapper.updateByNumber(orders);
        orderMapper.updateById(orders);

        // 封装信息
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("orderId", orders.getId());
        map.put("content", "订单号：" + ordersPaymentDTO.getOrderNumber());

        // 通过WebSocket发送信息
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }

    /**
     * 取消订单
     *
     * @param id 订单Id
     */
    @Override
    public void cancelOrder(Long id) {

        Orders orders = new Orders();

        // 设置订单Id
        orders.setId(id);

        // 设置订单状态：已取消
        orders.setStatus(Orders.CANCELLED);

        // 设置订单取消原因
        orders.setCancelReason("用户已取消");

        // 设置订单取消时间
        orders.setCancelTime(LocalDateTime.now());

        // 执行根据订单Id更新订单
        orderMapper.updateById(orders);
    }

}
