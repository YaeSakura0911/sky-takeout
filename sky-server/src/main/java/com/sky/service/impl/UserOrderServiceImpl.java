package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.Orders;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderMapper;
import com.sky.service.UserOrderService;
import com.sky.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 提交订单
     *
     * @param ordersSubmitDTO 提交订单DTO
     * @return OrderSubmitVO 提交订单VO
     */
    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        Orders orders = new Orders();

        BeanUtils.copyProperties(ordersSubmitDTO, orders);

        // 生成订单号
        String number = UUID.randomUUID().toString();
        // 设置订单号
        orders.setNumber(number);

        // 设置订单状态：1 - 待付款
        orders.setStatus(2);

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

        orderMapper.insert(orders);

        return new OrderSubmitVO(orders.getId(), number, orders.getAmount(), orders.getOrderTime());

    }
}
