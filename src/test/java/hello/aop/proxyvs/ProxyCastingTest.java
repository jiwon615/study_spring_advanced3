package hello.aop.proxyvs;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * JDK 동적 프록시와 CGLIB 의 타입 캐스팅 테스트
 */
@Slf4j
public class ProxyCastingTest {

    @Test
    @DisplayName("JDK 동적 프록시는 인터페이스를 기반으로 프록시를 생성 -> 인터페이스로 캐스팅 가능하지만, 그 구현체는 전혀 알지 못하는 한계")
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시 사용하여 인터페이스 기반 프록시 생성

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", memberServiceProxy.getClass());

        assertThatThrownBy(() -> {MemberServiceImpl castingMemberService = (MemberServiceImpl) proxyFactory.getProxy();})
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("CGLIB는 구체 클래스를 기반으로 프록시를 생성 -> 구체클래스 및 그 클래스의 인터페이스도 캐스팅 가능")
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 사용하여 구체 클래스 기반 프록시 생성

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberServiceProxy.getClass());

        // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        log.info("castingMemberService class={}", castingMemberService.getClass());
    }
}
