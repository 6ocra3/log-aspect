package org.makar.logstarter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.makar.logstarter.log.LoggingAspectStarter;
import org.makar.logstarter.log.LoggingProperties;

@Aspect
public class LogAspect {

    private final LoggingAspectStarter loggingAspectConfiguration;

    public LogAspect(LoggingProperties loggingProperties) {
        this.loggingAspectConfiguration = new LoggingAspectStarter(LogAspect.class, loggingProperties.getLevel());
    }

    @Around("@annotation(org.makar.logstarter.aspect.annotation.LogTimeExecution)")
    public Object logTimeExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Long start = System.nanoTime();
        Object result = null;
        String methodName = joinPoint.getSignature().getName();
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            loggingAspectConfiguration.log(String.format("%s @Around error %s", methodName, e));
        }
        Long end = System.nanoTime();
        double executionTimeInMs = (end - start) / 1_000_000.0;

        loggingAspectConfiguration.log(String.format("%s time execution is %s ms", methodName, executionTimeInMs));

        return result;
    }

    @AfterReturning(value = "@annotation(org.makar.logstarter.aspect.annotation.LogResult)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        loggingAspectConfiguration.log(String.format("%s result: %s", methodName, result));
    }

    @AfterThrowing(value = "@annotation(org.makar.logstarter.aspect.annotation.LogError)", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        loggingAspectConfiguration.log(String.format("%s ERROR: %s", methodName, e.getMessage()));
    }

    @AfterReturning(
            pointcut = "@annotation(org.makar.logstarter.aspect.annotation.LogHandlingDto)",
            returning = "result"
    )
    public void handlingTaskDtoResult(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        loggingAspectConfiguration.log(String.format("DTO of a method %s: %s", methodName, result.toString()));
    }
}
