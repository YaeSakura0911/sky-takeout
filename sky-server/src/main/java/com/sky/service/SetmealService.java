package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {

    /**
     * 根据分页查询套餐
     * @param categoryId 分类Id
     * @param name 名称
     * @param status 状态
     * @param page 页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    PageResult getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize);

    /**
     * 根据Id查询套餐
     * @param id 套餐Id
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void saveSetmeal(SetmealDTO setmealDTO);
}
