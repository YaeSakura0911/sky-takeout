package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

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

    /**
     * 根据Id删除口味信息
     * @param ids 菜品Id列表
     */
    void deleteDishFlavour(List<Long> ids);

    /**
     * 根据菜品Id删除口味信息
     * @param dishId 菜品Id
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id = #{dishId}")
    void deleteDishFlavourByDishId(Long dishId);

}
