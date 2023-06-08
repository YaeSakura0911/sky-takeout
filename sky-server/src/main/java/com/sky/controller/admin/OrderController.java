package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据Id查询订单详情
     *
     * @param id 订单Id
     * @return Result
     */
    @GetMapping("/details/{id}")
    public Result<OrderVO> getOrderById(@PathVariable Long id) {

        return Result.success(orderService.getOrderById(id));
    }

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
     * @return Result
     */
    @GetMapping("/conditionSearch")
    public Result<PageResult> getOrderByCondition(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            String number,
            Integer page,
            Integer pageSize,
            String phone,
            Integer status) {

        return Result.success(orderService.getOrderByCondition(beginTime, endTime, number, page, pageSize, phone, status));
    }

    /**
     * 获取各个状态订单数统计
     *
     * @return Result
     */
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> getOrderStatistics() {

        return Result.success(orderService.getOrderStatistics());
    }

    /**
     * 确认订单
     *
     * @param ordersConfirmDTO 确认订单DTO
     * @return Result
     */
    @PutMapping("/confirm")
    public Result<String> confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {

        orderService.confirmOrder(ordersConfirmDTO);

        return Result.success();
    }

    /**
     * 派送订单
     *
     * @param id 订单Id
     * @return Result
     */
    @PutMapping("/delivery/{id}")
    public Result<String> deliveryOrder(@PathVariable Long id) {

        orderService.deliveryOrder(id);

        return Result.success();
    }

    /**
     * 完成订单
     *
     * @param id 订单Id
     * @return Result
     */
    @PutMapping("/complete/{id}")
    public Result<String> completeOrder(@PathVariable Long id) {

        orderService.completeOrder(id);

        return Result.success();
    }

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单DTO
     * @return Result
     */
    @PutMapping("/cancel")
    public Result<String> cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO) {

        orderService.cancelOrder(ordersCancelDTO);

        return Result.success();
    }

    /**
     * 拒绝订单
     *
     * @param ordersRejectionDTO 拒绝订单DTO
     * @return Result
     */
    @PutMapping("/rejection")
    public Result<String> rejectionOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {

        orderService.rejectionOrder(ordersRejectionDTO);

        return Result.success();
    }
}
