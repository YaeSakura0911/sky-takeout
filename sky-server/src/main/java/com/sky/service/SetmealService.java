package com.sky.service;

import com.sky.result.PageResult;

public interface SetmealService {

    /**
     *
     * @param categoryId
     * @param name
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    PageResult getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize);
}
