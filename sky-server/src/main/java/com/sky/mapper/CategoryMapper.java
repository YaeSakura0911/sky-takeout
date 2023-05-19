package com.sky.mapper;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 根据分页查询分类
     * @param name 分类名称
     * @param type 分类类型
     * @return List
     */
    List<Category> getByPage(String name, Integer type);

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return List
     */
    @Select("SELECT * FROM category WHERE type = #{type}")
    List<Category> getByList(Integer type);

    /**
     * 更新分类
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 新增分类
     * @param category
     */
    @Insert("INSERT INTO category VALUE (null, #{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void saveCategory(Category category);

    /**
     * 删除分类
     * @param id 分类Id
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    void deleteCategory(Long id);
}
