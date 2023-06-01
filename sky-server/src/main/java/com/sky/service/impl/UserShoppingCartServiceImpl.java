package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.UserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShoppingCartServiceImpl implements UserShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 根据用户Id查询购物车
     * @return List<ShoppingCart>
     */
    @Override
    public List<ShoppingCart> getShoppingCart() {

        return shoppingCartMapper.selectShoppingCartByUserId(BaseContext.getCurrentId());
    }
}
