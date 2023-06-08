package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 根据Id查询订单
     *
     * @param id 订单Id
     * @return Orders
     */
    @Select("SELECT * FROM orders WHERE id = #{id}")
    Orders selectById(Long id);

    /**
     * 根据状态查询订单
     *
     * @param status 订单状态
     * @return List<Orders>
     */
    List<Orders> selectByUserIdAndStatus(Integer status);

    /**
     * 根据条件
     *
     * @return List<Orders>
     */
    List<Orders> selectByCondition(LocalDateTime beginTime, LocalDateTime endTime, String number, Integer page, Integer pageSize, String phone, Integer status);

    /**
     * 根据状态查询订单数
     *
     * @param status 订单状态
     * @return Integer
     */
    @Select("SELECT COUNT(*) FROM orders WHERE status = #{status}")
    Integer selectCountByStatus(Integer status);

    /**
     * 根据日期和状态查询订单数量
     *
     * @param status 订单状态
     * @param endDate 结束日期
     * @return Integer
     */
    @Select("SELECT COUNT(*) FROM orders WHERE status = #{status} AND order_time BETWEEN #{beginDate} AND #{endDate}")
    Integer selectCountByStatusAndDate(Integer status, LocalDate beginDate, LocalDate endDate);

    /**
     * 根据日期查询订单数量
     *
     * @return Integer
     */
    @Select("SELECT COUNT(*) FROM orders WHERE order_time BETWEEN #{beginDate} AND #{endDate}")
    Integer selectCountByDate(LocalDate beginDate, LocalDate endDate);

    /**
     * 根据日期查询订单
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return List<Order>
     */
    @Select("SELECT * FROM orders WHERE status = 5 AND order_time BETWEEN #{beginDate} AND #{endDate}")
    List<Orders> selectByCompleteAndDate(LocalDate beginDate, LocalDate endDate);

    /**
     * 插入订单
     *
     * @param orders 订单Entity
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO orders VALUE (null, #{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, null, #{payMethod}, #{payStatus}, #{amount}, #{remark}, #{phone}, #{address}, #{userName}, #{consignee}, #{cancelReason}, #{rejectionReason}, #{cancelTime}, #{estimatedDeliveryTime}, #{deliveryStatus}, #{deliveryTime}, #{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    void insert(Orders orders);

    /**
     * 根据订单号更新订单
     *
     * @param orders 订单Entity
     */
    @Update("UPDATE orders SET status = #{status}, checkout_time = #{checkoutTime}, pay_status = #{payStatus} WHERE number = #{number}")
    void updateByNumber(Orders orders);

    /**
     * 根据Id更新订单
     *
     * @param orders 订单Entity
     */
    @Update("UPDATE orders SET status = #{status}, cancel_reason = #{cancelReason}, cancel_time = #{cancelTime} WHERE id = #{id}")
    void updateById(Orders orders);
}
