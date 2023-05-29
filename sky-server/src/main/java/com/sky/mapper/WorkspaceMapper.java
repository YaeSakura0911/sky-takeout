package com.sky.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WorkspaceMapper {

    /**
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM dish WHERE status = 1")
    Integer selectEnableDish();

    /**
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM dish WHERE status = 0")
    Integer selectDisabledDish();

    /**
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM setmeal WHERE status = 1")
    Integer selectEnableSetmeal();

    /**
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM setmeal WHERE status = 0")
    Integer selectDisabledSetmeal();
}
