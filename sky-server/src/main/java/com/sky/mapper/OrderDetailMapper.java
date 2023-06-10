package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
     * 查询菜品销量前10
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return List<GoodsSalesDTO>
     */
    List<GoodsSalesDTO> selectTop10(LocalDate beginDate, LocalDate endDate);

    /**
     * 插入订单详情
     *
     * @param orderDetailList 订单详情列表
     */
    void insert(List<OrderDetail> orderDetailList);
}
