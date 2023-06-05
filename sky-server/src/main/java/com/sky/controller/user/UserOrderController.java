package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.UserOrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

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
     * 支付订单
     *
     * @param ordersPaymentDTO 支付订单DTO
     * @return Result
     */
    @PutMapping("/payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {

        return Result.success(userOrderService.payment(ordersPaymentDTO));
    }

}
