package ojosama.talkak.common.aop;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import ojosama.talkak.common.exception.TalKakException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* ojosama.talkak..controller..*(..))")
    public void controllerPointCut() {
    }

    @Pointcut("execution(* ojosama.talkak..service..*(..))")
    public void servicePointCut() {
    }

    @Pointcut("execution(* ojosama.talkak..domain..*(..))")
    public void domainPointCut() {
    }

    @Around("controllerPointCut()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long start = System.currentTimeMillis();
        log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            result = joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            log.info("Exit : {}.{}() with result = {} ({}ms)", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result, end - start);
        }
        return result;
    }

    @AfterThrowing(pointcut = "servicePointCut() || domainPointCut()", throwing = "e")
    public void logException(JoinPoint joinPoint, TalKakException e) {
        log.warn("Exception in {}.{}() with cause: {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getMessage(), e);
    }
}
