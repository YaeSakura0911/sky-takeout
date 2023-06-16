package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.*;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 查询菜品总览
     *
     * @return DishOverViewVO
     */
    @Override
    public DishOverViewVO getDishesOverview() {

        DishOverViewVO dishOverViewVO = new DishOverViewVO();

        // 查询并设置起售菜品数量
        dishOverViewVO.setSold(dishMapper.selectDishCount(1));

        // 查询并设置停售菜品数量
        dishOverViewVO.setDiscontinued(dishMapper.selectDishCount(0));

        return dishOverViewVO;

    }

    /**
     * 查询套餐总览
     *
     * @return SetmealOverViewVO
     */
    @Override
    public SetmealOverViewVO getSetmealOverview() {

        SetmealOverViewVO setmealOverViewVO = new SetmealOverViewVO();

        // 查询并设置起售套餐数量
        setmealOverViewVO.setSold(setmealMapper.selectSetmealCount(1));

        // 查询并设置停售套餐数量
        setmealOverViewVO.setDiscontinued(setmealMapper.selectSetmealCount(0));

        return setmealOverViewVO;
    }

    /**
     * 查询订单总览
     *
     * @return OrderOverViewVO
     */
    @Override
    public OrderOverViewVO getOrderOverview() {

        OrderOverViewVO orderOverViewVO = new OrderOverViewVO();

        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        // 查询并设置待接单订单数量
        orderOverViewVO.setWaitingOrders(orderMapper.selectCountByStatusAndDate(Orders.TO_BE_CONFIRMED, beginDate, endDate));

        // 查询并设置待派送订单数量
        orderOverViewVO.setDeliveredOrders(orderMapper.selectCountByStatusAndDate(Orders.CONFIRMED, beginDate, endDate));

        // 查询并设置已完成订单数量
        orderOverViewVO.setCompletedOrders(orderMapper.selectCountByStatusAndDate(Orders.COMPLETED, beginDate, endDate));

        // 查询并设置已取消订单数量
        orderOverViewVO.setCancelledOrders(orderMapper.selectCountByStatusAndDate(Orders.CANCELLED, beginDate, endDate));

        // 查询并设置全部订单数量
        orderOverViewVO.setAllOrders(orderMapper.selectCountByDate(beginDate, endDate));

        return orderOverViewVO;
    }

    /**
     * 查询今日运营数据
     *
     * @return BusinessDataVO
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDate begin, LocalDate end) {

        BusinessDataVO businessDataVO = new BusinessDataVO();

        LocalDate today = begin;
        BigDecimal totalAmount = new BigDecimal(0);
        Integer totalValidOrderCount = 0;
        Integer totalOrderCount = 0;

        while (!today.isEqual(end.plusDays(1))) {

            // 查询今日订单列表
            List<Orders> orders = orderMapper.selectByCompleteAndDate(today, today.plusDays(1));

            // 遍历订单列表
            for (Orders order : orders) {
                totalAmount = totalAmount.add(order.getAmount());
            }

            // 查询有效订单数量
            Integer validOrderCount = orderMapper.selectOrderCount(today, today.plusDays(1), Orders.COMPLETED);

            totalValidOrderCount += validOrderCount;

            // 查询订单总数量
            Integer orderCount = orderMapper.selectOrderCount(today, today.plusDays(1), null);

            totalOrderCount += orderCount;

            today = today.plusDays(1);
        }

        // 查询昨日用户数量
        Integer yesterdayUserCount = userMapper.selectUserCountByDateTime(today);
        // 查询今日用户数量
        Integer todayUserCount = userMapper.selectUserCountByDateTime(today.plusDays(1));

        // 设置营业额
        businessDataVO.setTurnover(totalAmount.doubleValue());

        // 设置有效订单数量
        businessDataVO.setValidOrderCount(totalValidOrderCount);

        // 设置订单完成率
        if (totalOrderCount == 0) {
            businessDataVO.setOrderCompletionRate(0.0);
        }
        else {
            businessDataVO.setOrderCompletionRate(totalValidOrderCount.doubleValue() / totalOrderCount.doubleValue());
        }

        // 设置平均客单价
        if (totalValidOrderCount == 0) {
            businessDataVO.setUnitPrice(0.0);
        }
        else {
            businessDataVO.setUnitPrice(totalAmount.divide(BigDecimal.valueOf(totalValidOrderCount), 2, BigDecimal.ROUND_DOWN).doubleValue());
        }

        // 设置新增用户数量
        businessDataVO.setNewUsers(todayUserCount - yesterdayUserCount);

        return businessDataVO;
    }

}
