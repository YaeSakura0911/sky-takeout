package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 根据Id查询菜品
     * @param id 菜品Id
     * @return DishVO
     */
    DishVO getById(Long id);

    /**
     * 根据分页查询菜品
     * @param categoryId 分类Id
     * @param name 菜品名称
     * @param status 菜品状态
     * @param page 页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    PageResult getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize);

    /**
     * 根据分类Id查询菜品
     * @param categoryId 分类Id
     * @return
     */
    List<Dish> getByList(Long categoryId);

    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveDish(DishDTO dishDTO);

    /**
     * 更新菜品
     * @param dishDTO
     */
    void updateDish(DishDTO dishDTO);

    /**
     * 起售、停售菜品
     * @param status 状态
     * @param id 菜品Id
     */
    void updateDishStatus(Integer status, Long id);

    /**
     * 删除菜品
     * @param ids 菜品Id列表
     */
    void deleteDish(Long[] ids);
}
