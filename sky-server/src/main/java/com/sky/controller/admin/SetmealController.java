package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
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
     * 根据分页查询套餐
     * @param categoryId 分类Id
     * @param name 套餐名称
     * @param status 套餐状态
     * @param page 页码
     * @param pageSize 分页大小
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
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    public Result<String> saveSetmeal(@RequestBody SetmealDTO setmealDTO) {

        setmealService.saveSetmeal(setmealDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result<String> updateSetmealStatus(Long id, @PathVariable Integer status) {

        setmealService.updateSetmealStatus(id, status);

        return Result.success();
    }

}
