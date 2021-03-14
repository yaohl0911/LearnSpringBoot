package com.yaohl0911.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j  // 这里复用了logback的日志实现，当然也可以用log4j
public class LogAspect {
    // 切入点
    // com.yaohl0911.service.*表示com.yaohl0911.service下所有包
    // 最后一个*表示所有方法，(..)表示方法的所有参数
    @Pointcut("execution(public * com.yaohl0911.service.*.*(..))")
    public void serviceLog() {
    }

    // 前置拦截 请求方法之前做拦截
    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("name:{}, value:{}", name, request.getParameter(name));
        }
    }

    // 响应信息 目标方法请求之后的log打印
    @AfterReturning(returning = "ret", pointcut = "serviceLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
}
