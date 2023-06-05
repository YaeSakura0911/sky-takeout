package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据套餐Id查询套餐
     * @param categoryId 分类Id
     * @param name 名称
     * @param status 状态
     * @return Page
     */
    Page<Setmeal> selectSetmealByPage(Long categoryId, String name, Integer status);

    /**
     * 根据Id查询套餐
     * @param id 套餐Id
     * @return Setmeal
     */
    @Select("SELECT * FROM setmeal WHERE id = #{id}")
    Setmeal selectSetmealById(Long id);

    /**
     * 根据分类Id查询套餐
     * @param categoryId 套餐Id
     * @return List<Setmeal> - 套餐列表
     */
    @Select("SELECT * FROM setmeal WHERE category_id = #{categoryId} AND status = 1")
    List<Setmeal> selectSetmealByCategoryId(Long categoryId);

    /**
     * 新增套餐
     * @param setmeal 套餐Entity
     */
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO setmeal VALUE (null, #{categoryId}, #{name}, #{price}, #{status}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insertSetmeal(Setmeal setmeal);

    /**
     * 更新套餐
     * @param setmeal 套餐Entity
     */
    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    /**
     * 批量删除套餐
     * @param ids 套餐Id列表
     */
    void deleteSetmeal(List<Long> ids);
}
