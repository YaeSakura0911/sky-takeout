package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.UserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserShoppingCartServiceImpl implements UserShoppingCartService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    /**
     * 根据用户Id查询购物车
     * @return List<ShoppingCart>
     */
    @Override
    public List<ShoppingCart> getShoppingCart() {

        return shoppingCartMapper.selectByUserId(BaseContext.getCurrentId());
    }

    /**
     * 添加购物车商品
     * @param shoppingCartDTO 购物车DTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        // 获取菜品Id
        Long dishId = shoppingCartDTO.getDishId();

        // 获取套餐Id
        Long setmealId = shoppingCartDTO.getSetmealId();

        // 获取菜品口味
        String dishFlavor = shoppingCartDTO.getDishFlavor();

        // 获取用户Id
        Long userId = BaseContext.getCurrentId();

        // 如果购物车DTO中的菜品Id不为空
        if (dishId != null) {

            // 根据用户Id和菜品Id查询购物车
            ShoppingCart shoppingCart = shoppingCartMapper.selectByUserIdAndDishId(userId, dishId);

            // 如果购物车为空
            if (shoppingCart == null) {

                shoppingCart = new ShoppingCart();

                // 根据菜品Id查询菜品信息
                Dish dish = dishMapper.selectDishById(shoppingCartDTO.getDishId());

                // 设置名称
                shoppingCart.setName(dish.getName());

                // 设置图片
                shoppingCart.setImage(dish.getImage());

                // 设置用户Id
                shoppingCart.setUserId(userId);

                // 设置菜品Id
                shoppingCart.setDishId(dishId);

                // 如果菜品口味不为空
                if (dishFlavor != null) {
                    // 设置菜品口味信息
                    shoppingCart.setDishFlavor(dishFlavor);
                }

                // 设置数量
                shoppingCart.setNumber(1);

                // 设置金额
                shoppingCart.setAmount(dish.getPrice());

                // 设置创建时间
                shoppingCart.setCreateTime(LocalDateTime.now());

                // 执行插入购物车SQL
                shoppingCartMapper.insert(shoppingCart);
            }

            // 取得菜品数量
            Integer number = shoppingCart.getNumber();

            // 取得总价
            BigDecimal amount = shoppingCart.getAmount();

            // 计算菜品单价
            BigDecimal price = amount.divide(BigDecimal.valueOf(number));

            // 菜品数量+1
            number += 1;

            // 计算新的总价
            amount = price.multiply(BigDecimal.valueOf(number));

            // 设置菜品数量
            shoppingCart.setNumber(number);

            // 设置菜品总价
            shoppingCart.setAmount(amount);

            // 执行根据用户Id和菜品Id更新购物车SQL
            shoppingCartMapper.updateByUserIdAndDishId(shoppingCart);

        }

        // 如果购物车DTO中的套餐Id不为空
        if (setmealId != null) {

            ShoppingCart shoppingCart = shoppingCartMapper.selectByUserIdAndSetmealId(userId, setmealId);

            // 如果购物车为空
            if (shoppingCart == null) {

                shoppingCart = new ShoppingCart();

                // 根据套餐Id查询套餐信息
                Setmeal setmeal = setmealMapper.selectSetmealById(shoppingCartDTO.getSetmealId());

                // 设置名称
                shoppingCart.setName(setmeal.getName());

                // 设置图片
                shoppingCart.setImage(setmeal.getImage());

                // 设置用户Id
                shoppingCart.setUserId(userId);

                // 设置套餐id
                shoppingCart.setSetmealId(setmealId);

                // 设置数量
                shoppingCart.setNumber(1);

                // 设置金额
                shoppingCart.setAmount(setmeal.getPrice());

                // 设置创建时间
                shoppingCart.setCreateTime(LocalDateTime.now());

                // 执行插入购物车SQL
                shoppingCartMapper.insert(shoppingCart);
            }

            // 取得菜品数量
            Integer number = shoppingCart.getNumber();

            // 取得总价
            BigDecimal amount = shoppingCart.getAmount();

            // 计算菜品单价
            BigDecimal price = amount.divide(BigDecimal.valueOf(number));

            // 菜品数量+1
            number += 1;

            // 计算新的总价
            amount = price.multiply(BigDecimal.valueOf(number));

            // 设置菜品数量
            shoppingCart.setNumber(number);

            // 设置菜品总价
            shoppingCart.setAmount(amount);

            // 执行根据用户Id和菜品Id更新购物车SQL
            shoppingCartMapper.updateByUserIdAndSetmealId(shoppingCart);

        }

    }

    /**
     * 减少购物车商品
     * @param shoppingCartDTO 购物车DTO
     */
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        // 获取菜品Id
        Long dishId = shoppingCartDTO.getDishId();

        // 获取套餐Id
        Long setmealId = shoppingCartDTO.getSetmealId();

        // 获取菜品口味
        String dishFlavor = shoppingCartDTO.getDishFlavor();

        // 获取用户Id
        Long userId = BaseContext.getCurrentId();

        if (dishId != null) {

            // 根据用户Id和菜品Id查询购物车
            ShoppingCart shoppingCart = shoppingCartMapper.selectByUserIdAndDishId(userId, dishId);

            // 取得菜品数量
            Integer number = shoppingCart.getNumber();

            // 取得总价
            BigDecimal amount = shoppingCart.getAmount();

            // 如果菜品数量等于1
            if (number == 1) {

                // 执行删除购物车
                shoppingCartMapper.deleteByUserIdAndDishId(userId, dishId);
            }
            else {

                // 计算菜品单价
                BigDecimal price = amount.divide(BigDecimal.valueOf(number));

                // 菜品数量-1
                number -= 1;

                // 计算新的总价
                amount = price.multiply(BigDecimal.valueOf(number));

                // 设置菜品数量
                shoppingCart.setNumber(number);

                // 设置菜品总价
                shoppingCart.setAmount(amount);

                // 执行根据用户Id和菜品Id更新购物车SQL
                shoppingCartMapper.updateByUserIdAndDishId(shoppingCart);
            }

        }

        // 如果购物车DTO中的套餐Id不为空
        if (setmealId != null) {

            ShoppingCart shoppingCart = shoppingCartMapper.selectByUserIdAndSetmealId(userId, setmealId);

            // 取得菜品数量
            Integer number = shoppingCart.getNumber();

            // 取得总价
            BigDecimal amount = shoppingCart.getAmount();

            // 如果数量等于1
            if (number == 1) {

                // 执行根据用户Id和套餐Id删除购物车SQL
                shoppingCartMapper.deleteByUserIdAndSetmealId(userId, setmealId);
            }
            else {
                // 计算菜品单价
                BigDecimal price = amount.divide(BigDecimal.valueOf(number));

                // 菜品数量-1
                number -= 1;

                // 计算新的总价
                amount = price.multiply(BigDecimal.valueOf(number));

                // 设置菜品数量
                shoppingCart.setNumber(number);

                // 设置菜品总价
                shoppingCart.setAmount(amount);

                // 执行根据用户Id和菜品Id更新购物车SQL
                shoppingCartMapper.updateByUserIdAndSetmealId(shoppingCart);
            }

        }

    }
}
