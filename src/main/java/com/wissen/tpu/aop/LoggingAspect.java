package com.wissen.tpu.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Aspect
@Component
public class LoggingAspect {

    private static final Log logger = LogFactory.getLog(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.wissen.tpu.controller.*.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] signatureArgs = joinPoint.getArgs();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Log Request
        try {
            if (signatureArgs.length > 0 && signatureArgs[0] != null) {
                logger.info(joinPoint.getSignature().getName()
                        + "\nRequest from T-app :" + mapper.writeValueAsString(signatureArgs[0]));
            }
        } catch (JsonProcessingException e) {
            // ignore logging errors
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = null;
        try {
            // 🚩 Let the controller/service run
            result = joinPoint.proceed();
            stopWatch.stop();

            // Log execution time
            logger.info("Execution time of " + className + "." + methodName
                    + " :: " + stopWatch.getTotalTimeMillis() + " ms");

            // Log Response (only on success)
            try {
                logger.info(joinPoint.getSignature().getName()
                        + "\nResponse to T-app :" + mapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                // ignore logging errors
            }

            return result;
        } catch (Throwable e) {
            stopWatch.stop();

            // Log execution time even on failure
            logger.info("Execution time of " + className + "." + methodName
                    + " :: " + stopWatch.getTotalTimeMillis() + " ms (with exception)");

            // Log exception
            logger.error("Exception in " + className + "." + methodName + " : " + e.getMessage(), e);

            // 🔥 CRITICAL: rethrow so GlobalExceptionHandler can handle it
            throw e;
        }
    }
}
