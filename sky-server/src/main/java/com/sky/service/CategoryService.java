package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 根据分页查询分类
     * @param name 分类名称
     * @param page 页码
     * @param pageSize 分页大小
     * @param type 分类类型
     * @return PageResult
     */
    PageResult getByPage(String name, Integer page, Integer pageSize, Integer type);

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return List
     */
    List<Category> getByList(Integer type);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void saveCategory(CategoryDTO categoryDTO);

    /**
     * 更新状态
     * @param status 分类状态
     * @param id 分类Id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 更新分类
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param id 分类Id
     */
    void deleteCategory(Long id);
}
