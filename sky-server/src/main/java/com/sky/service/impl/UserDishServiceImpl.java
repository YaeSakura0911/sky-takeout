package com.sky.service.impl;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavourMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.UserDishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDishServiceImpl implements UserDishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavourMapper dishFlavourMapper;

    /**
     * 根据分类Id查询菜品
     *
     * @param categoryId 分类Id
     * @return List<DishVO>
     */
    @Cacheable(cacheNames = "dish", key = "#categoryId")
    @Override
    public List<DishVO> getDishByCategoryId(Long categoryId) {

        List<Dish> dishList = dishMapper.selectDishByCategoryId(categoryId);

        // 遍历菜品列表
        List<DishVO> dishVOList = dishList.stream().map(dish -> {

            DishVO dishVO = new DishVO();

            BeanUtils.copyProperties(dish, dishVO);

            // 获取菜品口味列表
            List<DishFlavor> dishFlavorList = dishFlavourMapper.getByDishId(dish.getId());

            // 设置菜品口味
            dishVO.setFlavors(dishFlavorList);

            return dishVO;

        }).collect(Collectors.toList());

        return dishVOList;
    }
}
