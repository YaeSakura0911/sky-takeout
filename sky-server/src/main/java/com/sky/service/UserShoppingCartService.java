package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface UserShoppingCartService {

    /**
     * 根据用户Id查询购物车
     * @return List<ShoppingCart>
     */
    List<ShoppingCart> getShoppingCart();

    /**
     * 添加购物车商品
     * @param shoppingCartDTO 购物车DTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 减少购物车商品
     * @param shoppingCartDTO 购物车DTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车
     */
    void clearShoppingCart();
}
