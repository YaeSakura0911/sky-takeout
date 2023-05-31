package com.sky.service.impl;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.UserSetmealService;
import com.sky.vo.DishItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSetmealServiceImpl implements UserSetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 根据套餐Id查询菜品
     * @param id 套餐Id
     * @return List<DishItemVO>
     */
    @Override
    public List<DishItemVO> getSetmealDishById(Long id) {

        List<SetmealDish> setmealDishList = setmealDishMapper.selectSetmealDishBySetmealId(id);

        List<DishItemVO> dishItemVOList = setmealDishList.stream().map(setmealDish -> {

            Dish dish = dishMapper.selectDishById(setmealDish.getDishId());

            return new DishItemVO(setmealDish.getName(), setmealDish.getCopies(), dish.getImage(), dish.getDescription());
        }).collect(Collectors.toList());

        return dishItemVOList;
    }

    /**
     * 根据分类Id查询套餐
     * @param categoryId 分类Id
     * @return List<Setmeal>
     */
    @Override
    public List<Setmeal> getSetmealByCategoryId(Long categoryId) {

        return setmealMapper.selectSetmealByCategoryId(categoryId);
    }

}
