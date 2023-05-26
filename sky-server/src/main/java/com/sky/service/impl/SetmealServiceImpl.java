package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     *
     * @param categoryId 分类Id
     * @param name 名称
     * @param status 状态
     * @param page 页码
     * @param pageSize 分页大小
     * @return
     */
    @Override
    public PageResult getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        Page<Setmeal> setmealPage = setmealMapper.selectSetmealByPage(categoryId, name, status);

        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }
}
