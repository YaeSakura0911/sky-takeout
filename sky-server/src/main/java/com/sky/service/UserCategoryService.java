package com.sky.service;

import com.sky.entity.Category;

import java.util.List;

public interface UserCategoryService {

    /**
     * 根据分类类型查询分类
     * @param type 分类类型
     * @return List<Category> - 分类列表
     */
    List<Category> getCategoryForList(Integer type);
}
