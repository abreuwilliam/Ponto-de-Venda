package com.pdv.papelaria.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(com.pdv.papelaria.aop.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start();

        Object result = joinPoint.proceed();

        watch.stop();
        log.info("Tempo de execução do método {}: {} ms",
                joinPoint.getSignature().toShortString(),
                watch.getTotalTimeMillis());

        return result;
    }
}
