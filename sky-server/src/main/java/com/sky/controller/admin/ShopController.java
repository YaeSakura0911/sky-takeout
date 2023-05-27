package com.sky.controller.admin;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
public class ShopController {

    private static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询店铺状态
     * @return Result
     */
    @GetMapping("/status")
    public Result<Integer> getShopStatus() {

        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);

        return Result.success(status);
    }

    /**
     * 设置店铺状态
     * @param status 店铺状态
     * @return Result
     */
    @PutMapping("/{status}")
    public Result<String> setShopStatus(@PathVariable Integer status) {

        redisTemplate.opsForValue().set(KEY, status);

        return Result.success();
    }

}
