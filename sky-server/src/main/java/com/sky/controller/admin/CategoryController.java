package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "分类模块")
@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据分页查询分类
     * @param name 分类名称
     * @param page 页码
     * @param pageSize 分页大小
     * @param type 分类类型
     * @return Result
     */
    @GetMapping("/page")
    public Result<PageResult> getByPage(String name, Integer page, Integer pageSize, Integer type) {

        return Result.success(categoryService.getByPage(name, page, pageSize, type));
    }

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<Category>> getByList(Integer type) {

        return Result.success(categoryService.getByList(type));
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return Result
     */
    @PostMapping
    public Result<String> saveCategory(@RequestBody CategoryDTO categoryDTO) {

        categoryService.saveCategory(categoryDTO);

        return Result.success();
    }

    /**
     * 更新状态
     * @param status 分类状态
     * @param id 分类Id
     * @return Result
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {

        categoryService.updateStatus(status, id);

        return Result.success();
    }

    /**
     * 更新分类
     * @param categoryDTO
     * @return Result
     */
    @PutMapping
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {

        categoryService.updateCategory(categoryDTO);

        return Result.success();
    }

    /**
     * 删除分类
     * @param id 分类Id
     * @return Result
     */
    @DeleteMapping
    public Result<String> deleteCategory(Long id) {

        categoryService.deleteCategory(id);

        return Result.success();
    }
}
