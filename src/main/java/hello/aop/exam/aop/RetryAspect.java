package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value(); // 애노테이션에서 기본값으로 설정했던 3 받아옴
        Exception exceptionHolder = null;

        for (int retryCnt=1; retryCnt<=maxRetry; retryCnt++) {
            try {
                log.info("[retry] 시도횟수={}/{}", retryCnt, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }

}
