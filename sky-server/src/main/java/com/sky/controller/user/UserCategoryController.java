package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
public class UserCategoryController {

    @Autowired
    private UserCategoryService userCategoryService;

    /**
     * 根据分类类型查询分类
     * @param type 分类类型
     * @return List<Category> - 分类列表
     */
    @GetMapping("/list")
    public Result<List<Category>> getCategortForList(Integer type) {

        return Result.success(userCategoryService.getCategoryForList(type));
    }

}
