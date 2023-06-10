package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
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
    Dish selectDishById(Long id);

    /**
     * 根据分页查询菜品
     * @param categoryId 分类Id
     * @param name 菜品名称
     * @param status 菜品状态
     * @return List
     */
    List<Dish> selectDishByPage(Long categoryId, String name, Integer status);

    /**
     * 根据分类Id查询菜品
     * @param categoryId 分类Id
     * @return List<Dish> - 菜品列表
     */
    @Select("SELECT * FROM dish WHERE category_id = #{categoryId}")
    List<Dish> selectDishByCategoryId(Long categoryId);

    /**
     * 查询菜品数量
     * @param status 菜品状态
     * @return Integer
     */
    @Select("SELECT COUNT(*) FROM dish WHERE status = #{status}")
    Integer selectDishCount(Integer status);

    /**
     * 插入菜品
     * @param dish 菜品Entity
     */
    @AutoFill(OperationType.INSERT)
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("INSERT INTO dish VALUE (null, #{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insertDish(Dish dish);

    /**
     * 更新菜品
     * @param dish 菜品Entity
     */
    @AutoFill(OperationType.UPDATE)
    void updateDish(Dish dish);

    /**
     * 批量删除菜品
     * @param ids 菜品Id列表
     */
    void deleteDish(List<Long> ids);
}
