package com.rockyshen.aop;

import cn.hutool.json.JSONUtil;
import com.rockyshen.annotation.NoLogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2024/10/29 11:29
 *
 */

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Around("execution(* com.rockyshen.xxx.controller.*.*(..))")    // TODO:此处需要指定controller的包名
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        // 从joint实例中获取方法
        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        try {
            // 类全限定名.方法名
            log.info("接口方法：{}.{}",methodSignature.getDeclaringTypeName(),methodSignature.getName());

            // 获取所有参数，丢到map中，key为参数名，value为参数的值，然后将这个Map以JSON格式输出
            Map<String,Object> logParamsMap = new LinkedHashMap<>();
            String[] parameterNames = methodSignature.getParameterNames();
            Object[] args = point.getArgs();
            for (int i = 0; i <args.length; i++) {
                // 加了@NoLogAnnotation注解的参数，不加入map
                if(parameterIsLog(methodSignature,i)){
                    String parameterName = parameterNames[i];   // 参数名，形参
                    Object parameterValue = args[i];            // 参数值，实参

                    logParamsMap.put(parameterName,parameterValue);
                }
            }
            log.info("方法参数列表：{}", JSONUtil.toJsonStr(logParamsMap));
            result = point.proceed();
            return result;
        } finally {
            // 如果方法上加了@NoLogAnnotation注解，表示方法不记录返回值
            if(this.resultIsLog(methodSignature)){
                log.info("方法返回值：{}", JSONUtil.toJsonStr(result));
            }
        }
    }

    private boolean parameterIsLog(MethodSignature methodSignature, int paramIndex){
        if(methodSignature.getMethod().getParameterCount() == 0){
            return false;
        }
        // 检查参数上有没有@NoLogAnnotation注解
        Annotation[] parameterAnnotation = methodSignature.getMethod().getParameterAnnotations()[paramIndex];
        if(parameterAnnotation != null && parameterAnnotation.length > 0){
            for (Annotation annotation : parameterAnnotation){
                if(annotation.annotationType() == NoLogAnnotation.class){
                    return false;     // 加了这个注解，返回false，表示不允许加入日志
                }
            }
        }
        // 参数类型是：ServletRequest、ServletResponse  也不会记录！
        Class parameterType = methodSignature.getParameterTypes()[paramIndex];
        for(Class<?> type : noLogTypes){
            if(type.isAssignableFrom(parameterType)){
                return false;
            }
        }
        return true;
    }

    // 不记录的其他类型，目前写了：ServletRequest、ServletResponse
    private static List<Class<?>> noLogTypes = Arrays.asList(ServletRequest.class, ServletResponse.class);

    // 判断方法上是否加了@NoLogAnnotation注解，加了表示返回值不允许记录到日志中
    private boolean resultIsLog(MethodSignature methodSignature){
        return methodSignature.getMethod().getAnnotation(NoLogAnnotation.class) == null;
    }
}
