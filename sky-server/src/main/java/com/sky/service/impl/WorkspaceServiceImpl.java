package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.WorkspaceMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private WorkspaceMapper workspaceMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询菜品总览
     *
     * @return DishOverViewVO
     */
    @Override
    public DishOverViewVO getDishesOverview() {

        DishOverViewVO dishOverViewVO = new DishOverViewVO();

        dishOverViewVO.setSold(workspaceMapper.selectEnableDish());

        dishOverViewVO.setDiscontinued(workspaceMapper.selectDisabledDish());

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

        setmealOverViewVO.setSold(workspaceMapper.selectEnableSetmeal());

        setmealOverViewVO.setDiscontinued(workspaceMapper.selectDisabledSetmeal());

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

        orderOverViewVO.setWaitingOrders(orderMapper.selectCountByStatusAndDate(Orders.TO_BE_CONFIRMED, beginDate, endDate));

        orderOverViewVO.setDeliveredOrders(orderMapper.selectCountByStatusAndDate(Orders.CONFIRMED, beginDate, endDate));

        orderOverViewVO.setCompletedOrders(orderMapper.selectCountByStatusAndDate(Orders.COMPLETED, beginDate, endDate));

        orderOverViewVO.setCancelledOrders(orderMapper.selectCountByStatusAndDate(Orders.CANCELLED, beginDate, endDate));

        orderOverViewVO.setAllOrders(orderMapper.selectCountByDate(beginDate, endDate));

        return orderOverViewVO;
    }

}
