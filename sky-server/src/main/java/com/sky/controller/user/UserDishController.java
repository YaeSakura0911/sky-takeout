package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.UserDishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
public class UserDishController {

    @Autowired
    private UserDishService userDishService;

    /**
     * 根据分类Id查询菜品
     *
     * @param categoryId 分类Id
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<DishVO>> getDishByCategoryId(Long categoryId) {

        return Result.success(userDishService.getDishByCategoryId(categoryId));
    }

}
