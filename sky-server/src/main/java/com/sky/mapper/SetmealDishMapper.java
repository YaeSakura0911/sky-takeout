package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 新增套餐菜品
     * @param setmealDishList 套餐菜品列表
     */
    void insertSetmealDish(List<SetmealDish> setmealDishList);

    /**
     *
     * @param dishId
     * @return
     */
    @Select("SELECT * FROM setmeal_dish WHERE dish_id = #{dishId}")
    List<SetmealDish> selectSetmealDishByDishId(Long dishId);
}
