package com.tadpole.cloud.operationManagement.modular.shopEntity.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(*  com.tadpole.cloud.operationManagement.modular.shopEntity.service.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint)  {
        System.out.println("secondIncome日志: 方法执行前");
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();



        System.out.println("日志: 方法执行前 - 方法名: " + methodName);
        System.out.println("日志: 入参 - " + getMethodParameters(args));

    }

    @After("execution(* com.tadpole.cloud.operationManagement.modular.shopEntity.service.*.*(..))")
    public void logAfterMethodExecution() {
        System.out.println("secondIncome日志: 方法执行后");
        System.out.println(new Date());

    }


    @Around("execution(* com.tadpole.cloud.operationManagement.modular.shopEntity.service.*.*(..))")
    public Object logMethodPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();  // Proceed with the actual method execution

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Object[] args = joinPoint.getArgs();



        String methodName = joinPoint.getSignature().toShortString();
        System.out.println("方法: " + methodName + " 执行时间 " + duration + "ms");
        System.out.println("方法参数: " + getMethodParameters(args) );

        return result;
    }

    private String getMethodParameters(Object[] args) {
        StringBuilder parameters = new StringBuilder();
        for (Object arg : args) {
            if (arg != null) {
                parameters.append(arg.toString()).append(", ");
            } else {
                parameters.append("null, ");
            }
        }
        if (parameters.length() > 0) {
            parameters.setLength(parameters.length() - 2);  // Remove the last comma and space
        }
        return parameters.toString();
    }
}
