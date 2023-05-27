package com.sky.mapper;

import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据Id查询菜品
     * @param id 菜品Id
     * @return Dish
     */
    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish getById(Long id);

    /**
     * 根据分页查询菜品
     * @param categoryId 分类Id
     * @param name 菜品名称
     * @param status 菜品状态
     * @return List
     */
    List<Dish> getByPage(Long categoryId, String name, Integer status);

    /**
     * 根据分类Id查询菜品
     * @param categoryId 分类Id
     * @return
     */
    @Select("SELECT * FROM dish WHERE category_id = #{categoryId}")
    List<Dish> selectDishByCategoryId(Long categoryId);

    /**
     * 插入菜品
     * @param dish
     * @return Long 菜品Id
     */
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("INSERT INTO dish VALUE (null, #{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    Long insertDish(Dish dish);

    /**
     * 更新菜品
     * @param dish
     */
    void updateDish(Dish dish);

    /**
     * 删除菜品
     * @param ids 菜品Id列表
     */
    void deleteDish(List<Long> ids);
}
