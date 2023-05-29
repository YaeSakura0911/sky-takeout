package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavourMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishFlavourMapper dishFlavourMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 根据Id查询菜品
     * @param id 菜品Id
     * @return DishVO
     */
    @Override
    public DishVO getById(Long id) {

        DishVO dishVO = new DishVO();

        // 查询菜品信息
        Dish dish = dishMapper.selectDishById(id);

        // 查询分类信息
        Category category = categoryMapper.getById(dish.getCategoryId());

        // 查询风味信息
        List<DishFlavor> dishFlavorList = dishFlavourMapper.getByDishId(dish.getId());

        // 构建DishVO
        BeanUtils.copyProperties(dish, dishVO);

        // 设置分类名称
        dishVO.setCategoryName(category.getName());

        // 设置菜品风味
        dishVO.setFlavors(dishFlavorList);

        return dishVO;
    }

    /**
     * 根据分页查询菜品
     * @param categoryId 分类Id
     * @param name 菜品名称
     * @param status 菜品状态
     * @param page 页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    @Override
    public PageResult getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize) {

        // 设置分页参数
        PageHelper.startPage(page, pageSize);

        // 执行分页查询
        List<Dish> dishList = dishMapper.selectDishByPage(categoryId, name, status);

        // 获取查询结果
        Page<Dish> dishPage = (Page<Dish>) dishList;

        // 构建DishVO
        List<DishVO> dishVOList = dishPage.getResult().stream().map(dish -> {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish, dishVO);

            // 查询分类信息
            Category category = categoryMapper.getById(dish.getCategoryId());

            // 设置分类名称
            dishVO.setCategoryName(category.getName());

            return dishVO;
        }).collect(Collectors.toList());

        return new PageResult(dishPage.getTotal(), dishVOList);
    }

    /**
     * 根据分类Id查询菜品
     * @param categoryId 分类Id
     * @return List 菜品列表
     */
    @Override
    public List<Dish> getByList(Long categoryId) {

        return dishMapper.selectDishByCategoryId(categoryId);
    }

    /**
     * 新增菜品
     * @param dishDTO 菜品DTO
     */
    @Transactional
    @Override
    public void saveDish(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO, dish);

        // 插入菜品创建、更新时间
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());

        // 插入菜品创建、更新用户
        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateUser(BaseContext.getCurrentId());

        // 插入菜品信息
        dishMapper.insertDish(dish);

        // 取得菜品风味信息
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

        if (dishFlavorList.size() > 0) {
            // 设置菜品风味的菜品Id
            dishFlavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });

            // 插入菜品口味信息
            dishFlavourMapper.insertDishFlavour(dishFlavorList);
        }

    }

    /**
     * 更新菜品
     * @param dishDTO 菜品DTO
     */
    @Transactional
    @Override
    public void updateDish(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO, dish);

        // 设置菜品更新时间
        dish.setUpdateTime(LocalDateTime.now());

        // 设置菜品更新用户
        dish.setUpdateUser(BaseContext.getCurrentId());

        // 执行更新菜品SQL
        dishMapper.updateDish(dish);

        // 执行删除菜品口味SQL
        dishFlavourMapper.deleteDishFlavourByDishId(dishDTO.getId());

        // 如果菜品口味不为空
        if (dishDTO.getFlavors().size() > 0) {

            List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

            // 遍历菜品口味列表
            dishFlavorList.forEach(dishFlavor -> {
                // 设置菜品Id
                dishFlavor.setDishId(dishDTO.getId());
            });

            // 执行插入菜品口味SQL
            dishFlavourMapper.insertDishFlavour(dishFlavorList);
        }

    }

    /**
     * 起售、停售菜品
     * @param status 状态
     * @param id 菜品Id
     */
    @Override
    public void updateDishStatus(Integer status, Long id) {

        Dish dish = new Dish();

        // 设置Id
        dish.setId(id);
        // 设置状态
        dish.setStatus(status);

        // 设置更新时间
        dish.setUpdateTime(LocalDateTime.now());

        // 设置更新用户
        dish.setUpdateUser(BaseContext.getCurrentId());

        dishMapper.updateDish(dish);

    }

    /**
     * 批量删除菜品
     * @param ids 菜品Id列表
     */
    @Transactional
    @Override
    public void deleteDish(Long[] ids) {

        List<Long> dishIdList = Arrays.asList(ids);

        // 遍历菜品Id列表
        dishIdList.forEach(dishId -> {
            // 执行根据菜品Id查询菜品
            Dish dish = dishMapper.selectDishById(dishId);

            // 执行根据菜品Id查询套餐菜品
            List<SetmealDish> setmealDishList = setmealDishMapper.selectSetmealDishByDishId(dishId);

            // 如果菜品已起售
            if (dish.getStatus() == 1) {
                // 抛出DeletionNotAllowedException异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }

            // 如果套餐菜品列表不为空
            if (setmealDishList != null) {
                // 抛出DeletionNotAllowedException异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        });

        // 执行删除菜品SQL
        dishMapper.deleteDish(Arrays.asList(ids));

        // 执行删除菜品相关的口味SQL
        dishFlavourMapper.deleteDishFlavour(Arrays.asList(ids));

    }
}
