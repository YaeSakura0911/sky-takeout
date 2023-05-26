package com.sky.controller.admin;

import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     *
     * @param categoryId
     * @param name
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize) {

        return Result.success(setmealService.getByPage(categoryId, name, status, page, pageSize));
    }
}
