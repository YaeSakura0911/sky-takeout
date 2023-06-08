package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    /**
     * 根据openId查询用户
     * @param openId openId
     * @return User
     */
    @Select("SELECT * FROM user WHERE openid = #{openId}")
    User selectUserByOpenId(String openId);

    /**
     * 根据日期时间查询用户数量
     *
     * @param endDate 结束时间
     * @return Integer - 用户数量
     */
    @Select("SELECT COUNT(*) FROM user WHERE create_time < #{endDate}")
    Integer selectUserCountByDateTime(LocalDate endDate);

    /**
     * 插入用户
     * @param user 用户Entity
     */
    @Insert("INSERT INTO user VALUE (null, #{openid}, null, null, null, null, null, #{createTime})")
    void insertUser(User user);
}
