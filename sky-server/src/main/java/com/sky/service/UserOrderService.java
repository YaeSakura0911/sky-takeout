package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface UserOrderService {

    /**
     * 根据Id查询订单
     *
     * @param id 订单Id
     * @return Result
     */
    OrderVO getById(Long id);

    /**
     * 查询历史订单
     *
     * @param status 订单状态
     * @param page 当前页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    PageResult getOrderForPage(Integer status, Integer page, Integer pageSize);

    /**
     * 催单
     *
     * @param id 订单Id
     */
    void reminderOrder(Long id);

    /**
     * 提交订单
     *
     * @param ordersSubmitDTO 提交订单DTO
     * @return OrderSubmitVO
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 重复订单
     *
     * @param id 订单Id;
     */
    void repeatOrder(Long id);

    /**
     * 支付订单
     *
     * @param ordersPaymentDTO 支付订单DTO
     */
    void paymentOrder(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 取消订单
     *
     * @param id 订单Id
     */
    void cancelOrder(Long id);

}
