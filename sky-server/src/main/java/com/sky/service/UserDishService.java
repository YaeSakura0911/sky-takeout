package com.sky.service;

import com.sky.vo.DishVO;

import java.util.List;

public interface UserDishService {

    /**
     * 根据分类Id查询菜品
     *
     * @param categoryId 分类Id
     * @return List<DishVO>
     */
    List<DishVO> getDishByCategoryId(Long categoryId);
}
