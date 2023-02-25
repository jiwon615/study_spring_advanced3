package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * AOP 구현 - 다양한 어드바이스 종류 활용
 */
@Slf4j
@Aspect
public class AspectV6AdviceTypes {

    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature()); // 1
            // @Before // 2
            Object result = joinPoint.proceed(); // 2 이후 타겟 로직 실행
            // @AfterReturning // 3
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature()); // 5
            return result;
        } catch (Exception e) {
            // @AfterThrowing // 3-1
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature()); // 5-1
            throw e;
        } finally {
            // @After   // 4
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature()); // 6
        }
    }

    @Before("hello.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning("hello.aop.order.aop.PointCuts.orderAndService()")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("[afterReturning] {}", joinPoint.getSignature());
    }

    @AfterThrowing("hello.aop.order.aop.PointCuts.orderAndService()")
    public void afterThrowing(JoinPoint joinPoint) {
        log.info("[afterThrowing] {}", joinPoint.getSignature());
    }

    @After("hello.aop.order.aop.PointCuts.orderAndService()")
    public void after(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
