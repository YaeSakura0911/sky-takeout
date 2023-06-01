package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据用户Id查询购物车
     * @param userId 用户Id
     * @return List<ShoppingCart>
     */
    @Select("SELECT * FROM shopping_cart WHERE user_id = #{userId}")
    List<ShoppingCart> selectShoppingCartByUserId(Long userId);
}
