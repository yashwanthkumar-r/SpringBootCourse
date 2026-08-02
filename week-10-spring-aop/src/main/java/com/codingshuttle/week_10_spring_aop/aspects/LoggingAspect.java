package com.codingshuttle.week_10_spring_aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    // Matches any method named orderPackage in any Spring-managed bean,
    // regardless of return type or number/type of arguments.
    // (..) means zero or more arguments of any type,
    //@Before("execution(* orderPackage(..))")

    // Matches only the orderPackage method declared in ShipmentServiceImpl,
    //@Before("execution(* com.codingshuttle.week_10_spring_aop.service.impl.shipmentServiceImpl.orderPackage(..))")

    // Matches every method inside every class directly under the impl package.
    @Before("execution(* com.codingshuttle.week_10_spring_aop.service.impl.*.*(..))")
    public void beforeOrderPackage(JoinPoint joinPoint){
        // Logs the join-point type, like method, field or class
        log.info("Before called from LoggingAspect kind: {}",joinPoint.getKind());

        //logs the method signature(method-return type, arguments, full method path) of the joint-point
        log.info("Before called from LoggingAspect method signature: {}", joinPoint.getSignature());
    }

    //Matches all the classes inside impl
    //@Before("within(com.codingshuttle.week_10_spring_aop.service.impl.*)")

    //Matches all the sub packages and classes inside week_10_spring_aop
    @Before("within(com.codingshuttle.week_10_spring_aop..*)")
    public void beforeServiceImplCalls(){
        log.info("Service Impl calls");
    }

    //Matches any methods that has Transactional annotation
    //@Before("@annotation(org.springframework.transaction.annotation.Transactional)")

    //Matches any methods that has our custom annotation
    @Before("@annotation(com.codingshuttle.week_10_spring_aop.aspects.MyLogging)")
    public void beforeTransactionAnnotationCalls(){
        log.info("Before MyLogging annotation calls");
    }

    @After("myLoggingAndAopMethodPointCut()")
    public void afterMyLoggingAndAopMethodPointCut(){
        log.info("After My Logging Annotation calls");
    }


    @Pointcut("@annotation(com.codingshuttle.week_10_spring_aop.aspects.MyLogging) && within(com.codingshuttle.week_10_spring_aop..*)")
    public void myLoggingAndAopMethodPointCut(){
        //this method name will be the name of this POINT-CUT,
        // this pointcut exec logs when any methods have "MyLogging" annotation and that methods should be inside "week_10_spring_aop"
    }
}
