package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.UserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
public class UserShoppingCartController {

    @Autowired
    private UserShoppingCartService userShoppingCartService;

    /**
     * 根据用户Id查询购物车
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> getShoppingCart() {

        return Result.success(userShoppingCartService.getShoppingCart());
    }

    /**
     * 添加购物车商品
     * @param shoppingCartDTO 购物车DTO
     * @return Result
     */
    @PostMapping("/add")
    public Result<String> addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        userShoppingCartService.addShoppingCart(shoppingCartDTO);

        return Result.success();
    }

    /**
     * 减少购物车商品
     * @param shoppingCartDTO 购物车DTO
     * @return Result
     */
    @PostMapping("/sub")
    public Result<String> subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        userShoppingCartService.subShoppingCart(shoppingCartDTO);

        return Result.success();
    }

    /**
     * 清空购物车
     * @return Result
     */
    @DeleteMapping("/clean")
    public Result<String> clearShoppingCart() {

        userShoppingCartService.clearShoppingCart();

        return Result.success();
    }

}
