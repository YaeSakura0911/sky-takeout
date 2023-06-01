package com.sky.service.impl;

import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.UserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShoppingCartServiceImpl implements UserShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

}
