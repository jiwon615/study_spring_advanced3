package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Pointcut 외부로 빼서 사용
 * - 외부에서 호출 시, pointcut의 접근제어자는 public 으로 해야함
 */
public class PointCuts {

    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {}

    // 타입 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    // allOrder() && allService()
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}

}
