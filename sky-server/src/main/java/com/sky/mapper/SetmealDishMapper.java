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
     * @param setmealId 套餐Id
     * @return
     */
    @Select("SELECT * FROM setmeal_dish WHERE setmeal_id = #{setmealId}")
    List<SetmealDish> selectSetmealDishBySetmealId(Long setmealId);
}
