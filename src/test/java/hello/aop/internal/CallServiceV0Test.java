package hello.aop.internal;

import hello.aop.internal.aop.CallAspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callService;

    @Test
    @DisplayName("external은 CallAspect 어드바이스 적용되지만, 내부 호출인 internal은 미적용됨")
    void external() {
        callService.external();
    }

    @Test
    @DisplayName("프록시 거치기 때문에 CallAspect 어드바이스 적용됨")
    void internal() {
        callService.internal();
    }
}