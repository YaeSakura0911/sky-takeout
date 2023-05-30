package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final String WECHAT_LOGIN_API = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 用户登录
     * @param userLoginDTO 用户登录DTO
     * @return UserLoginVO
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {

        // 获取授权码
        String code = userLoginDTO.getCode();
        // 获取openid
        String openId = getOpenId(code);

        // 如果openId为空
        if (openId == null) {
            // 抛出LoginFailedException异常
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 执行根据openId查询用户SQL
        User user = userMapper.selectUserByOpenId(openId);

        // 如果用户为空
        if (user == null) {
            user = new User();
            // 设置openId
            user.setOpenid(openId);
            // 设置创建时间
            user.setCreateTime(LocalDateTime.now());
            // 执行插入用户SQL
            userMapper.insertUser(user);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        // 生成JWT
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = new UserLoginVO();

        // 设置用户Id
        userLoginVO.setId(user.getId());
        // 设置openId
        userLoginVO.setOpenid(user.getOpenid());
        // 设置Token
        userLoginVO.setToken(token);

        return userLoginVO;
    }

    /**
     * 获取openId
     * @param code 授权码
     * @return String - openId
     */
    private String getOpenId(String code) {

        // 配置请求参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");

        // 发送登录请求
        String json = HttpClientUtil.doGet(WECHAT_LOGIN_API, paramMap);

        // 将字符串转换成JSON
        JSONObject jsonObject = JSON.parseObject(json);
        // 从JSON中解析出openid

        return jsonObject.getString("openid");
    }

}
