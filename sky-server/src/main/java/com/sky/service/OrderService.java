package com.sky.service;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;

import java.time.LocalDateTime;

public interface OrderService {

    /**
     * 根据Id查询订单
     *
     * @param id 订单Id
     * @return OrderVO
     */
    OrderVO getOrderById(Long id);

    /**
     * 搜索订单
     *
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param number 订单号
     * @param page 当前页码
     * @param pageSize 分页大小
     * @param phone 电话
     * @param status 订单状态
     * @return PageResult
     */
    PageResult getOrderByCondition(LocalDateTime beginTime, LocalDateTime endTime, String number, Integer page, Integer pageSize, String phone, Integer status);

    /**
     * 获取各个状态订单数统计
     *
     * @return OrderStatisticsVO
     */
    OrderStatisticsVO getOrderStatistics();

    /**
     * 确认订单
     *
     * @param ordersConfirmDTO 确认订单DTO
     */
    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 派送订单
     *
     * @param id 订单Id
     */
    void deliveryOrder(Long id);

    /**
     * 完成订单
     *
     * @param id 订单Id
     */
    void completeOrder(Long id);

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单DTO
     */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    /**
     * 拒绝订单
     *
     * @param ordersRejectionDTO 拒绝订单DTO
     */
    void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO);
}
