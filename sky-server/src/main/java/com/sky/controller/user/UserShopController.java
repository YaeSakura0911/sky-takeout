package com.sky.controller.user;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shop")
public class UserShopController {

    private static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 获取店铺状态
     * @return Result
     */
    @GetMapping("/status")
    public Result<Integer> getShopStatus() {

        return Result.success(redisTemplate.opsForValue().get(KEY));
    }

}
