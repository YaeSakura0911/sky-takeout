package com.sky.service;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;

import java.util.List;

public interface UserSetmealService {

    /**
     * 根据套餐Id查询菜品
     * @param id 套餐Id
     * @return List<DishItemVO> -
     */
    List<DishItemVO> getSetmealDishById(Long id);

    /**
     * 根据分类Id查询套餐
     * @param categoryId 分类Id
     * @return List<Setmeal>
     */
    List<Setmeal> getSetmealByCategoryId(Long categoryId);
}
