package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavourMapper {

    /**
     * 根据菜品Id查询风味信息
     * @param dishId 菜品Id
     * @return List
     */
    @Select("SELECT * FROM dish_flavor WHERE dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);

    /**
     * 新增菜品口味信息
     * @param dishFlavorList 菜品口味信息列表
     */
    void insertDishFlavour(List<DishFlavor> dishFlavorList);

    /**
     *
     * @param dishFlavorList
     */
    // void updateDishFlavour(List<DishFlavor> dishFlavorList);

}
