package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 根据分页查询套餐
     * @param categoryId 分类Id
     * @param name 名称
     * @param status 状态
     * @param page 页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    @Override
    public PageResult getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        Page<Setmeal> setmealPage = setmealMapper.selectSetmealByPage(categoryId, name, status);

        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }

    /**
     * 根据Id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {

        SetmealVO setmealVO = new SetmealVO();

        Setmeal setmeal = setmealMapper.selectSetmealById(id);

        BeanUtils.copyProperties(setmeal, setmealVO);

        List<SetmealDish> setmealDishList = setmealDishMapper.selectSetmealDishByDishId(id);

        setmealVO.setSetmealDishes(setmealDishList);

        return setmealVO;
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void saveSetmeal(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 设置创建、更新时间
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());

        // 设置创建、更新用户
        setmeal.setCreateUser(BaseContext.getCurrentId());
        setmeal.setUpdateUser(BaseContext.getCurrentId());

        setmealMapper.insertSetmeal(setmeal);

        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();

        // 设置套餐菜品的套餐Id
        setmealDishList.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });

        setmealDishMapper.insertSetmealDish(setmealDishList);

    }
}
