package com.sky.service;

import com.sky.entity.ShoppingCart;

import java.util.List;

public interface UserShoppingCartService {

    /**
     * 根据用户Id查询购物车
     * @return List<ShoppingCart>
     */
    List<ShoppingCart> getShoppingCart();
}
