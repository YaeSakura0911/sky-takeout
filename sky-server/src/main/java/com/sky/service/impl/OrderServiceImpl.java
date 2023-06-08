package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 根据Id查询订单
     *
     * @param id 订单Id
     * @return OrderVO
     */
    @Override
    public OrderVO getOrderById(Long id) {

        // 根据Id查询订单
        Orders orders = orderMapper.selectById(id);

        OrderVO orderVO = new OrderVO();

        BeanUtils.copyProperties(orders, orderVO);

        // 根据订单Id查询订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(id);

        // 设置订单详情列表
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    /**
     * 搜索订单
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param number    订单号
     * @param page      当前页码
     * @param pageSize  分页大小
     * @param phone     电话
     * @param status    订单状态
     * @return PageResult
     */
    @Override
    public PageResult getOrderByCondition(LocalDateTime beginTime, LocalDateTime endTime, String number, Integer page, Integer pageSize, String phone, Integer status) {

        PageHelper.startPage(page, pageSize);

        // 根据条件分页查询订单
        Page<Orders> ordersPage = (Page<Orders>) orderMapper.selectByCondition(beginTime, endTime, number, page, pageSize, phone, status);

        // 遍历订单列表
        List<OrderVO> orderVOList = ordersPage.getResult().stream().map(orders -> {

            OrderVO orderVO = new OrderVO();

            BeanUtils.copyProperties(orders, orderVO);

            // 根据订单Id查询订单详情
            List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(orders.getId());

            StringBuilder orderDishes = new StringBuilder();

            // 遍历订单详情列表
            orderDetailList.forEach(orderDetail -> {
                // 拼接菜品数量信息
                orderDishes.append(orderDetail.getName()).append(" * ").append(orderDetail.getNumber()).append("; ");
            });

            // 设置订单菜品信息
            orderVO.setOrderDishes(orderDishes.toString());

            return orderVO;
        }).collect(Collectors.toList());

        return new PageResult(ordersPage.getTotal(), orderVOList);
    }

    /**
     * 获取各个状态订单数统计
     *
     * @return OrderStatisticsVO
     */
    @Override
    public OrderStatisticsVO getOrderStatistics() {

        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();

        // 查询并设置待接单订单数量
        orderStatisticsVO.setToBeConfirmed(orderMapper.selectCountByStatus(Orders.TO_BE_CONFIRMED));

        // 查询并设置已接单订单数量
        orderStatisticsVO.setConfirmed(orderMapper.selectCountByStatus(Orders.CONFIRMED));

        // 查询并设置派送中订单数量
        orderStatisticsVO.setDeliveryInProgress(orderMapper.selectCountByStatus(Orders.DELIVERY_IN_PROGRESS));

        return orderStatisticsVO;
    }

    /**
     * 确认订单
     *
     * @param ordersConfirmDTO 确认订单DTO
     */
    @Override
    public void confirmOrder(OrdersConfirmDTO ordersConfirmDTO) {

        // 根据Id查询订单
        Orders orders = orderMapper.selectById(ordersConfirmDTO.getId());

        // 设置订单状态：已接单
        orders.setStatus(Orders.CONFIRMED);

        // 执行更新订单SQL
        orderMapper.updateById(orders);
    }

    /**
     * 派送订单
     *
     * @param id 订单Id
     */
    @Override
    public void deliveryOrder(Long id) {

        // 根据Id查询订单
        Orders orders = orderMapper.selectById(id);

        // 设置订单状态：派送中
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);

        // 执行更新订单SQL
        orderMapper.updateById(orders);
    }

    /**
     * 完成订单
     *
     * @param id 订单Id
     */
    public void completeOrder(Long id) {

        // 根据Id查询订单
        Orders orders = orderMapper.selectById(id);

        // 设置订单状态：已完成
        orders.setStatus(Orders.COMPLETED);

        // 执行更新订单SQL
        orderMapper.updateById(orders);
    }

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单DTO
     */
    @Override
    public void cancelOrder(OrdersCancelDTO ordersCancelDTO) {

        // 根据Id查询订单
        Orders orders = orderMapper.selectById(ordersCancelDTO.getId());

        // 设定订单状态：已取消
        orders.setStatus(Orders.CANCELLED);
        // 设置取消原因
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        // 设置取消时间
        orders.setCancelTime(LocalDateTime.now());

        // 执行更新订单SQL
        orderMapper.updateById(orders);
    }

    /**
     * 拒绝订单
     *
     * @param ordersRejectionDTO 拒绝订单DTO
     */
    @Override
    public void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO) {

        // 根据Id查询订单
        Orders orders = orderMapper.selectById(ordersRejectionDTO.getId());

        // TODO：设置订单状态
        orders.setStatus(Orders.CANCELLED);

        // 设置拒绝原因
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());

        // 执行更新订单SQL
        orderMapper.updateById(orders);

    }
}
