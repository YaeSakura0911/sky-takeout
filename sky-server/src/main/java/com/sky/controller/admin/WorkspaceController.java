package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 查询菜品总览
     *
     *
     */
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> getDishesOverview() {

        return Result.success(workspaceService.getDishesOverview());
    }

    /**
     * 查询套餐总览
     *
     * @return Result
     */
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> getSetmealOverview() {

        return Result.success(workspaceService.getSetmealOverview());
    }

    /**
     * 查询订单总览
     *
     * @return Result
     */
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> getOrderOverview() {

        return Result.success(workspaceService.getOrderOverview());
    }

    /**
     * 查询今日运营数据
     *
     * @return Result
     */
    @GetMapping("/businessData")
    public Result<BusinessDataVO> getBusinessData() {

        return Result.success(workspaceService.getBusinessData());
    }

}
