package com.sky.service.impl;

import com.sky.entity.Category;
import com.sky.mapper.UserCategoryMapper;
import com.sky.service.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    @Autowired
    private UserCategoryMapper userCategoryMapper;

    /**
     * 根据分类类型查询分类
     * @param type 分类类型
     * @return List<Category> - 分类列表
     */
    @Override
    public List<Category> getCategoryForList(Integer type) {
        return userCategoryMapper.selectCategoryByType(type);
    }
}
