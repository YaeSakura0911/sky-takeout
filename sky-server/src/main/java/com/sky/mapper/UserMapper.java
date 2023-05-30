package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     * 插入用户
     * @param user 用户Entity
     */
    @Insert("INSERT INTO user VALUE (null, #{openid}, null, null, null, null, null, #{createTime})")
    void insertUser(User user);
}
