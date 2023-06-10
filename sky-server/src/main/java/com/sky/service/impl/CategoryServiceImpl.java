package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 根据分页查询分类
     * @param name 分类名称
     * @param page 页码
     * @param pageSize 分页大小
     * @param type 分类类型
     * @return PageResult
     */
    @Override
    public PageResult getByPage(String name, Integer page, Integer pageSize, Integer type) {

        PageHelper.startPage(page, pageSize);

        List<Category> categoryList = categoryMapper.getByPage(name, type);

        Page<Category> categoryPage = (Page<Category>) categoryList;

        return new PageResult(categoryPage.getTotal(), categoryPage.getResult());
    }

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return List 分类列表
     */
    @Override
    public List<Category> getByList(Integer type) {

        return categoryMapper.getByList(type);
    }

    /**
     * 新增分类
     * @param categoryDTO 分类DTO
     */
    @Override
    public void saveCategory(CategoryDTO categoryDTO) {

        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO, category);

        // 设置分类状态
        category.setStatus(StatusConstant.ENABLE);

        // 设置创建时间
        category.setCreateTime(LocalDateTime.now());

        // 设置更新时间
        category.setUpdateTime(LocalDateTime.now());

        // 设置创建员工
        category.setCreateUser(BaseContext.getCurrentId());

        // 设置更新员工
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.saveCategory(category);
    }

    /**
     * 更新状态
     * @param status 分类状态
     * @param id 分类Id
     */
    @Override
    public void updateStatus(Integer status, Long id) {

        Category category = new Category();

        // 设置分类Id
        category.setId(id);

        // 设置分类状态
        category.setStatus(status);

        // 设置更新时间
        category.setUpdateTime(LocalDateTime.now());

        // 设置更新员工
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.updateCategory(category);
    }

    /**
     * 更新分类
     * @param categoryDTO 分类DTO
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {

        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO, category);

        // 设置更新时间
        category.setUpdateTime(LocalDateTime.now());

        // 设置更新员工
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.updateCategory(category);
    }

    /**
     * 删除分类
     * @param categoryId 分类Id
     */
    @Override
    public void deleteCategory(Long categoryId) {

        // 执行根据分类Id查询套餐SQL
        List<Setmeal> setmealList = setmealMapper.selectSetmealByCategoryId(categoryId);

        // 如果套餐列表不为空
        if (setmealList.size() > 0) {
            //  抛出DeletionNotAllowedException异常
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        // 执行根据分类Id查询菜品SQL
        List<Dish> dishList = dishMapper.selectDishByCategoryId(categoryId);

        // 如果菜品列表不为空
        if (dishList.size() > 0) {
            // 抛出DeletionNotAllowedException异常
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        // 执行删除分类SQL
        categoryMapper.deleteCategory(categoryId);
    }
}
