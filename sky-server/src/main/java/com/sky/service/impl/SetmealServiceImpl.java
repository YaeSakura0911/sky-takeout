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
import com.sky.exception.DeletionNotAllowedException;
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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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

        // 遍历套餐菜品列表
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
     * @return SetmealVO 套餐VO
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
     * @param setmealDTO 套餐DTO
     */
    @Transactional
    @Override
    public void saveSetmeal(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 设置创建、更新时间
        // setmeal.setCreateTime(LocalDateTime.now());
        // setmeal.setUpdateTime(LocalDateTime.now());

        // 设置创建、更新用户
        // setmeal.setCreateUser(BaseContext.getCurrentId());
        // setmeal.setUpdateUser(BaseContext.getCurrentId());

        setmealMapper.insertSetmeal(setmeal);

        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();

        // 遍历套餐菜品列表
        setmealDishList.forEach(setmealDish -> {
            // 设置套餐菜品的套餐Id
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
                Dish dish = dishMapper.selectDishById(setmealDish.getDishId());

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
        // setmeal.setUpdateTime(LocalDateTime.now());

        // 设置更新员工
        // setmeal.setUpdateUser(BaseContext.getCurrentId());

        // 执行更新套餐状态信息
        setmealMapper.updateSetmeal(setmeal);
    }

    /**
     * 更新套餐
     * @param setmealDTO 套餐DTO
     */
    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 设置更新时间
        // setmeal.setUpdateTime(LocalDateTime.now());

        // 设置更新用户
        // setmeal.setUpdateUser(BaseContext.getCurrentId());

        // 执行更新套餐SQL
        setmealMapper.updateSetmeal(setmeal);

        // 执行删除套餐菜品SQL
        setmealDishMapper.deleteSetmealDishBySetmealId(setmealDTO.getId());

        // 如果套餐菜品列表不为空
        if (setmealDTO.getSetmealDishes().size() > 0) {

            List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();

            // 遍历套餐菜品列表
            setmealDishList.forEach(setmealDish -> {
                // 设置套餐Id
                setmealDish.setSetmealId(setmealDTO.getId());
            });

            // 执行插入套餐菜品SQL
            setmealDishMapper.insertSetmealDish(setmealDishList);

        }
    }

    /**
     * 批量删除套餐
     * @param ids 套餐Id列表
     */
    @Transactional
    @Override
    public void deleteSetmeal(Long[] ids) {

        List<Long> setmealIdList = Arrays.asList(ids);

        // 遍历套餐Id列表
        setmealIdList = setmealIdList.stream().peek(id -> {
            // 根据Id查询套餐信息
            Setmeal setmeal = setmealMapper.selectSetmealById(id);

            // 如果套餐状态为起售
            if (setmeal.getStatus() == 1) {
                // 抛出DeletionNotAllowed异常
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }

        }).collect(Collectors.toList());

        // 执行批量删除套餐
        setmealMapper.deleteSetmeal(setmealIdList);

        // 执行批量删除套餐菜品
        setmealDishMapper.deleteSetmealDish(setmealIdList);
    }
}
