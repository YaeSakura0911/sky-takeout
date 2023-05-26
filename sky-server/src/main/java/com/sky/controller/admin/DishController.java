package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 根据Id查询菜品
     * @param id 菜品Id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {

        return Result.success(dishService.getById(id));
    }

    /**
     * 根据分页查询菜品
     * @param categoryId 分类Id
     * @param name 菜品名称
     * @param status 菜品状态
     * @param page 页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    @GetMapping("/page")
    public Result<PageResult> getByPage(Long categoryId, String name, Integer status, Integer page, Integer pageSize) {

        return Result.success(dishService.getByPage(categoryId, name, status, page, pageSize));
    }

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result<String> saveDish(@RequestBody DishDTO dishDTO) {

        dishService.saveDish(dishDTO);

        return Result.success();
    }

}
