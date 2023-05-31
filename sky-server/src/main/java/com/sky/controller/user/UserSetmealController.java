package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.UserSetmealService;
import com.sky.vo.DishItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/setmeal")
public class UserSetmealController {

    @Autowired
    private UserSetmealService userSetmealService;

    /**
     * 根据套餐Id查询菜品
     * @param id 套餐Id
     * @return Result
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getSetmealDishById(@PathVariable Long id) {

        return Result.success(userSetmealService.getSetmealDishById(id));
    }

    /**
     * 根据分类Id查询套餐
     * @param categoryId 分类Id
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> getSetmealByCategoryId(Long categoryId) {

        return Result.success(userSetmealService.getSetmealByCategoryId(categoryId));
    }

}
