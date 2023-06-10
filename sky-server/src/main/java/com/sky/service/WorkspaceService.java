package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

public interface WorkspaceService {

    /**
     * 查询菜品总览
     *
     * @return DishOverViewVO
     */
    DishOverViewVO getDishesOverview();

    /**
     * 查询套餐总览
     *
     * @return SetmealOverViewVO
     */
    SetmealOverViewVO getSetmealOverview();

    /**
     * 查询订单总览
     *
     * @return OrderOverViewVO
     */
    OrderOverViewVO getOrderOverview();

    /**
     * 查询今日运营数据
     *
     * @return BusinessDataVO
     */
    BusinessDataVO getBusinessData();
}
