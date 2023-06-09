package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.UserOrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    /**
     * 根据Id查询订单
     *
     * @param id 订单Id
     * @return Result
     */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> getOrderById(@PathVariable Long id) {

        return Result.success(userOrderService.getById(id));
    }

    /**
     * 查询历史订单
     *
     * @param status 订单状态
     * @param page 当前页码
     * @param pageSize 分页大小
     * @return Result
     */
    @GetMapping("/historyOrders")
    public Result<PageResult> getOrderForPage(Integer status, Integer page, Integer pageSize) {

        return Result.success(userOrderService.getOrderForPage(status, page, pageSize));
    }

    /**
     * 催单
     *
     * @param id 订单Id
     * @return Result
     */
    @GetMapping("/reminder/{id}")
    public Result<String> reminderOrder(@PathVariable Long id) {

        userOrderService.reminderOrder(id);

        return Result.success();
    }

    /**
     * 提交订单
     *
     * @param ordersSubmitDTO 提交订单DTO
     * @return Result
     */
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {

        return Result.success(userOrderService.submitOrder(ordersSubmitDTO));
    }

    /**
     * 重复订单
     *
     * @param id 订单Id
     * @return Result
     */
    @PostMapping("/repetition/{id}")
    public Result<String> repeatOrder(@PathVariable Long id) {

        userOrderService.repeatOrder(id);

        return Result.success();
    }

    /**
     * 支付订单
     *
     * @param ordersPaymentDTO 支付订单DTO
     * @return Result
     */
    @PutMapping("/payment")
    public Result<String> paymentOrder(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {

        userOrderService.paymentOrder(ordersPaymentDTO);

        return Result.success();
    }

    /**
     * 取消订单
     *
     * @param id 订单Id
     * @return Result
     */
    @PutMapping("/cancel/{id}")
    public Result<String> cancelOrder(@PathVariable Long id) {

        userOrderService.cancelOrder(id);

        return Result.success();
    }

}
