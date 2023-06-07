package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 根据订单Id查询订单详情
     *
     * @param orderId 订单Id
     * @return List<OrderDetail>
     */
    @Select("SELECT * FROM order_detail WHERE order_id = #{orderId}")
    List<OrderDetail> selectByOrderId(Long orderId);

    /**
     * 插入订单详情
     *
     * @param orderDetailList 订单详情列表
     */
    void insert(List<OrderDetail> orderDetailList);
}
