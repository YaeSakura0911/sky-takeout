package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 根据用户Id查询地址
     * @param userId 用户Id
     * @return List<AddressBook> - 地址列表
     */
    @Select("SELECT * FROM address_book WHERE user_id = #{userId}")
    List<AddressBook> selectByUserId(Long userId);

    /**
     * 新增地址
     * @param addressBook 地址Entity
     */
    @Insert("INSERT INTO address_book VALUE (null, #{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(AddressBook addressBook);
}
