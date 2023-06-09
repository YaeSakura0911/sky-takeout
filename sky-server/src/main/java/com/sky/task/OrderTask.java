package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 每分钟清理支付超时订单
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void cleanPaymentTimeoutOrder() {

        // 根据支付状态和截止时间获取订单
        List<Orders> ordersList = orderMapper.selectByStatusAndEndTime(Orders.PENDING_PAYMENT, LocalDateTime.now().minusMinutes(15));

        // 如果订单列表不为空
        if (ordersList != null) {
            // 遍历订单列表
            ordersList.forEach(orders -> {
                // 设置订单状态：已取消
                orders.setStatus(Orders.CANCELLED);
                // 设置取消原因
                orders.setCancelReason("支付超时");
                // 设置取消时间
                orders.setCancelTime(LocalDateTime.now());

                // 执行更新订单SQL
                orderMapper.updateById(orders);
            });
        }
    }

    /**
     * 每6小时清理派送超时订单
     */
    @Scheduled(cron = "0 0 0/6 * * *")
    public void cleanDeliveryTimeoutOrder() {

        // 根据支付状态和截止时间获取订单
        List<Orders> ordersList = orderMapper.selectByStatusAndEndTime(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().minusHours(1));

        // 如果订单列表不为空
        if (ordersList != null) {
            // 遍历订单列表
            ordersList.forEach(orders -> {
                // 设置订单状态：已完成
                orders.setStatus(Orders.COMPLETED);
                // 执行更新订单SQL
                orderMapper.updateById(orders);
            });
        }
    }
}
