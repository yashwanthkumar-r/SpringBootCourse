package com.codingshuttle.week_10_spring_aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidationAspect {

    @Pointcut("execution(* com.codingshuttle.week_10_spring_aop.service.impl.*.*(..))")
    public void allServiceMethodsPointCut(){
    }

    @Around("allServiceMethodsPointCut()")
    public Object validateOrderId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        Long orderId = (Long)args[0];

        //runs the method only if the orderID is > 0
        if(orderId > 0) return proceedingJoinPoint.proceed();

        //else return below string without executing the method
        return "Can't call with negative order id";
    }
}
