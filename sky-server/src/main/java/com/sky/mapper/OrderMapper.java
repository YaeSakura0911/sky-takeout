package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     *
     * @param orders 订单Entity
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO orders VALUE (null, #{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, null, #{payMethod}, #{payStatus}, #{amount}, null, null, null, null, null, null, null, null, null, #{deliveryStatus}, #{deliveryTime}, #{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    void insert(Orders orders);
}
