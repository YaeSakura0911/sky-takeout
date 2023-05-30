package com.sky.mapper;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCategoryMapper {

    /**
     * 根据分类类型查询分类
     * @param type 分类类型
     * @return List<Category> - 分类列表
     */
    List<Category> selectCategoryByType(Integer type);
}
