package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 기본 AOP 구현
 */
@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* hello.aop.order..*(..))") // hello.aop.order 패키지와 하위 패키지를 저장하는 포인트컷 표현식
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {  // doLog()는 Advice
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }

}
