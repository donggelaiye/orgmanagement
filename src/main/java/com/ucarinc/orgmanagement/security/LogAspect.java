package com.ucarinc.orgmanagement.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description: 日志切面模板类
 * @author 陈锦宾 （1164857566@qq.com）
 * @date 2019-07-26 10:08:00
 * @version 1.0
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 创建日志对象
     * 可使用@slf4j注解方式，需添加Lombok依赖
     */
    private Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 设置切点方法
     * 对com.ucarinc.structure.controller下的所有类的所有方法切面
     */
    @Pointcut("execution(public * com.ucarinc.orgmanagement.controller..*Controller.*(..))")
    public void MethodPointcut() { }

    /**
     * 环绕通知方法,可自定义目标方法执行的时机
     * @param point JoinPoint的子接口,添加了
     *            Object proceed() throws Throwable 执行目标方法
     *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
     *            两个方法
     * @return 此方法需要返回值,返回值视为目标方法的返回值
     */
    @Around("MethodPointcut()")
    public Object Around(ProceedingJoinPoint point) throws Throwable {
        log.info("开始方法："+ point.getSignature().getName());
        Object object = point.proceed();
        //可添加执行某些功能
        log.info("完成方法" + "\n");
        return object;
    }

}
