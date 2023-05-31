package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import com.sky.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class MapperAspect {

    @Before("@annotation(com.sky.annotation.AutoFill)")
    public void AutoFill(JoinPoint joinPoint) {

        // 获取方法签名对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法上的注解对象
        AutoFill autoFill = methodSignature.getMethod().getDeclaredAnnotation(AutoFill.class);
        // 获取注解的value
        OperationType operationType = autoFill.value();

        // 获取方法参数数组
        Object[] joinPointArgs = joinPoint.getArgs();

        // 如果参数数组为空或长度等于0
        if (joinPointArgs == null || joinPointArgs.length == 0) {
            return;
        }

        // 获取参数数组中的第一个参数
        Object joinPointArg = joinPointArgs[0];
        // 获取参数的字节码
        Class<?> joinPointArgClass = joinPointArg.getClass();

        // 如果注解的值为INSERT
        if (operationType.equals(OperationType.INSERT)) {

            try {
                // 获取setCreateTime、setCreateUser、setUpdateTime、setUpdateUser方法
                Method setCreateTimeMethod = joinPointArgClass.getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTimeMethod = joinPointArgClass.getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUserMethod = joinPointArgClass.getMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUserMethod = joinPointArgClass.getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射执行方法赋值
                setCreateTimeMethod.invoke(joinPointArg, LocalDateTime.now());
                setUpdateTimeMethod.invoke(joinPointArg, LocalDateTime.now());
                setCreateUserMethod.invoke(joinPointArg, BaseContext.getCurrentId());
                setUpdateUserMethod.invoke(joinPointArg, BaseContext.getCurrentId());
            } catch (Exception e) {
                // 打印错误日志
                log.error(e.getMessage());
                // 抛出BaseException异常
                throw new BaseException(MessageConstant.UNKNOWN_ERROR);
            }
        }
        // 如果注解的值为UPDATE
        else if (operationType.equals(OperationType.UPDATE)) {

            try {
                // 获取setUpdateTime、setUpdateUser方法
                Method setUpdateTimeMethod = joinPointArgClass.getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUserMethod = joinPointArgClass.getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射执行方法赋值
                setUpdateTimeMethod.invoke(joinPointArg, LocalDateTime.now());
                setUpdateUserMethod.invoke(joinPointArg, BaseContext.getCurrentId());
            } catch (Exception e) {
                // 打印错误日志
                log.error(e.getMessage());
                // 抛出BaseExceptio异常
                throw new BaseException(MessageConstant.UNKNOWN_ERROR);
            }
        }
    }
}
