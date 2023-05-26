package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavourMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /**
     * 根据Id查询菜品
     * @param id 菜品Id
     * @return DishVO
     */
    @Override
    public DishVO getById(Long id) {

        DishVO dishVO = new DishVO();

        // 查询菜品信息
        Dish dish = dishMapper.getById(id);

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
        List<Dish> dishList = dishMapper.getByPage(categoryId, name, status);

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
     * 新增菜品
     * @param dishDTO
     */
    @Transactional
    @Override
    public void saveDish(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO, dish);

        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());

        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateUser(BaseContext.getCurrentId());

        // 插入菜品信息
        dishMapper.insertDish(dish);

        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

        dishFlavorList.forEach(dishFlavor -> {
            dishFlavor.setDishId(dish.getId());
        });

        // 插入菜品口味信息
        dishFlavourMapper.insertDishFlavour(dishFlavorList);

    }
}
