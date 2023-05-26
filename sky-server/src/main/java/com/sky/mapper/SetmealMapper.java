package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

    /**
     *
     * @param categoryId
     * @param name
     * @param status
     * @return
     */
    Page<Setmeal> selectSetmealByPage(Long categoryId, String name, Integer status);
}
