package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
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
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
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

        List<SetmealVO> setmealVOList = setmealPage.getResult().stream().map(setmeal -> {

            SetmealVO setmealVO = new SetmealVO();

            // 将setmeal复制到setmealVO
            BeanUtils.copyProperties(setmeal, setmealVO);

            // 根据Id查询分类
            Category category = categoryMapper.getById(setmeal.getCategoryId());

            // 设置分类名称
            setmealVO.setCategoryName(category.getName());

            return setmealVO;
        }).collect(Collectors.toList());

        return new PageResult(setmealPage.getTotal(), setmealVOList);
    }

    /**
     * 根据Id查询套餐
     * @param id 套餐Id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {

        SetmealVO setmealVO = new SetmealVO();

        // 查询套餐信息
        Setmeal setmeal = setmealMapper.selectSetmealById(id);

        // 将setmeal复制到setmealVO
        BeanUtils.copyProperties(setmeal, setmealVO);

        // 查询套餐菜品信息
        List<SetmealDish> setmealDishList = setmealDishMapper.selectSetmealDishBySetmealId(id);

        // 设置套餐菜品信息
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

    /**
     * 更新套餐状态
     * @param id 套餐Id
     * @param status 套餐状态
     */
    @Override
    public void updateSetmealStatus(Long id, Integer status) {

        // 如果是起售套餐
        if (status == 1) {
            // 根据Id查询当前套餐的所有菜品
            List<SetmealDish> setmealDishList = setmealDishMapper.selectSetmealDishBySetmealId(id);

            // 遍历所有菜品
            setmealDishList.forEach(setmealDish -> {
                Dish dish = dishMapper.getById(setmealDish.getDishId());

                // 如果有未起售的菜品
                if (dish.getStatus() == 0) {
                    // 抛出SetmealEnableFailed异常
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            });
        }

        Setmeal setmeal = new Setmeal();

        // 设置套餐Id
        setmeal.setId(id);
        // 设置套餐状态
        setmeal.setStatus(status);

        // 设置更新时间
        setmeal.setUpdateTime(LocalDateTime.now());

        // 设置更新员工
        setmeal.setUpdateUser(BaseContext.getCurrentId());

        // 执行更新套餐状态信息
        setmealMapper.updateSetmeal(setmeal);
    }
}
