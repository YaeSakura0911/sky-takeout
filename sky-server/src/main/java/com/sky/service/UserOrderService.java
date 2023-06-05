package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;

public interface UserOrderService {

    /**
     * 提交订单
     *
     * @param ordersSubmitDTO 提交订单DTO
     * @return OrderSubmitVO
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 支付订单
     *
     * @param ordersPaymentDTO 支付订单DTO
     * @return OrderPaymentVO
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);

}
