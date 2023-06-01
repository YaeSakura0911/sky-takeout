package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 如果当前拦截到的不是Controller方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中获取token
        String token = request.getHeader(jwtProperties.getUserTokenName());

        // 校验令牌
        try {
            // 从token中解析出负载
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

            // 从负载中解析出用户Id
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            // 将用户Id保存到ThreadLocal
            BaseContext.setCurrentId(userId);

            return true;
        } catch (Exception e) {
            // 返回状态码：401
            response.setStatus(401);
            return false;
        }

    }
}
