package ojosama.talkak.common.aop;

import lombok.extern.slf4j.Slf4j;
import ojosama.talkak.common.exception.TalKakException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @AfterThrowing(pointcut = "execution(* ojosama.talkak..*(..))", throwing = "e")
    public void logException(JoinPoint joinPoint, TalKakException e) {
        log.warn("Exception in {}.{}() with cause: {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getMessage(), e);
    }
}
