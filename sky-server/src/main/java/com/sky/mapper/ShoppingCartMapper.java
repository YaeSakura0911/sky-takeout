package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据用户Id查询购物车
     * @param userId 用户Id
     * @return List<ShoppingCart> - 购物车列表
     */
    @Select("SELECT * FROM shopping_cart WHERE user_id = #{userId}")
    List<ShoppingCart> selectByUserId(Long userId);

    /**
     * 根据用户Id和菜品Id查询购物车
     * @param userId 用户Id
     * @param dishId 菜品Id
     * @return ShoppingCart - 购物车Entity
     */
    @Select("SELECT * FROM shopping_cart WHERE user_id = #{userId} AND dish_id = #{dishId}")
    ShoppingCart selectByUserIdAndDishId(Long userId, Long dishId);

    /**
     * 根据用户Id和套餐Id查询购物车
     * @param userId 用户Id
     * @param setmealId 套餐Id
     * @return ShoppingCart - 购物车Entity
     */
    @Select("SELECT * FROM shopping_cart WHERE user_id = #{userId} AND dish_id = #{setmealId}")
    ShoppingCart selectByUserIdAndSetmealId(Long userId, Long setmealId);

    /**
     * 插入购物车
     * @param shoppingCart 购物车Entity
     */
    void insert(ShoppingCart shoppingCart);

    /**
     * 根据用户Id和菜品Id更新购物车
     * @param shoppingCart 购物车Entity
     */
    @Update("UPDATE shopping_cart SET number = #{number}, amount = #{amount} WHERE user_id = #{userId} AND dish_id = #{dishId}")
    void updateByUserIdAndDishId(ShoppingCart shoppingCart);

    /**
     * 根据用户Id和套餐Id
     * @param shoppingCart 购物车Entity
     */
    @Update("UPDATE shopping_cart SET number = #{number}, amount = #{amount} WHERE user_id = #{userId} AND setmeal_id = #{setmealId}")
    void updateByUserIdAndSetmealId(ShoppingCart shoppingCart);

}
