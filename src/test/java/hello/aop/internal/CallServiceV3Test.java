package hello.aop.internal;

import hello.aop.internal.aop.CallAspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallAspect.class)
@SpringBootTest
public class CallServiceV3Test {

    @Autowired
    CallServiceV3 callServiceV3;

    @Test
    @DisplayName("구조 변경")
    void external() {
        callServiceV3.external();
    }
}
