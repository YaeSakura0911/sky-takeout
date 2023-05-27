package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据Id查询套餐
     * @param id 套餐Id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {

        return Result.success(setmealService.getById(id));
    }

    /**
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    public Result<String> saveSetmeal(@RequestBody SetmealDTO setmealDTO) {

        setmealService.saveSetmeal(setmealDTO);

        return Result.success();
    }
}
