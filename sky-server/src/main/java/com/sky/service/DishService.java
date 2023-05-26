package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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
     * 新增菜品
     * @param dishDTO
     */
    void saveDish(DishDTO dishDTO);
}
