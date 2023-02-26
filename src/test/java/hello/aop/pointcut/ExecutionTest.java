package hello.aop.pointcut;

import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exeuction 문법
 * - execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
 */
@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("MemberServiceImpl.hello(string) 메소드의 정보를 출럭")
    void printMethod() {
        // 결과 : public java.lang.String hello.aop.order.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName("메소드와 가장 정확하게 매칭되는 표현식")
    void exactMatch() {
        pointcut.setExpression("execution(public String hello.aop.order.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue(); // pointcut.matches(메소드, 대상클래스)로 매칭 여부 boolean 값 얻을 수 있음
    }

    @Test
    @DisplayName("가장 많이 생략한 조건")
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메소드 이름 관련 포인트컷1")
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메소드 이름 관련 포인트컷2")
    void nameMatchStar2() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메소드 이름 관련 포인트컷3")
    void nameMatchStar3() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메소드 이름 관련 포인트컷4 - no match")
    void nameMatchStar4() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("패키지 매칭 관련 포인트컷1 - exact match")
    void packageMatch1() {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 관련 포인트컷2 - start이용")
    void packageMatch2() {
        pointcut.setExpression("execution(* hello.aop.order.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 관련 포인트컷3 - 틀리게 start 이용")
    void packageMatch3() {
        pointcut.setExpression("execution(* hello.aop.order.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("패키지 매칭 관련 포인트컷4 -  .. 이용")
    void packageMatch4() {
        pointcut.setExpression("execution(* hello.aop.order.member..*.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 관련 포인트컷5 - .. 이용")
    void packageMatch5() {
        pointcut.setExpression("execution(* hello.aop..*.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입 매칭1")
    void typeMatch1() {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입 매칭2 - 부모 타입 허용")
    void typeMatch2() {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입 매칭3 - 자식 타입에 있는 다른 메소드 메시되는지")
    void typeMatch3() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입 매칭4 - 부모 타입에서는 자식 타입에만 있는 메소드 허용 x")
    void typeMatch4() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.order.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("파라미터 매칭1 - String 타입의 파라미터 허용")
    void argsMatch1() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터 매칭2 - 파라미터가 없어야 함")
    void argsMatch2() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("파라미터 매칭3 - 정확히 하나의 파라미터 허용, 모든 타입 허용")
    void argsMatch3() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터 매칭4 - 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
    void argsMatch4() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터 매칭5 - String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
    void argsMatch5() {
        // (String), (String, Xxx), (String, Xxx, Xxx) 허용
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
