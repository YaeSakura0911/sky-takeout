package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
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
     * 根据套餐Id查询套餐菜品
     * @param setmealId 套餐Id
     * @return
     */
    @Select("SELECT * FROM setmeal_dish WHERE setmeal_id = #{setmealId}")
    List<SetmealDish> selectSetmealDishBySetmealId(Long setmealId);

    /**
     * 根据菜品Id查询套餐菜品
     * @param dishId 菜品Id
     * @return
     */
    @Select("SELECT * FROM setmeal_dish WHERE dish_id = #{dishId}")
    List<SetmealDish> selectSetmealDishByDishId(Long dishId);

    /**
     * 批量删除套餐菜品
     * @param setmealIdList
     */
    void deleteSetmealDish(List<Long> setmealIdList);

    /**
     * 根据套餐Id删除套餐菜品
     * @param setmealId 套餐Id
     */
    @Delete("DELETE FROM setmeal_dish WHERE setmeal_id = #{setmealId}")
    void deleteSetmealDishBySetmealId(Long setmealId);
}
