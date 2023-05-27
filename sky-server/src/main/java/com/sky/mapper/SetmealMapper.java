package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM setmeal WHERE id = #{id}")
    Setmeal selectSetmealById(Long id);

    /**
     * 新增套餐
     * @param setmeal
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO setmeal VALUE (null, #{categoryId}, #{name}, #{price}, #{status}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insertSetmeal(Setmeal setmeal);
}
