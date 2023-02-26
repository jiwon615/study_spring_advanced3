package hello.aop.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 대안1 - 자기 자신 주입
 * 참고: 생성자 주입은 순환 사이클을 만들기 때문에 실패 함
 */
@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    // 생성자 주입은 순환 사이클을 만들기 때문에 실패 함
//    public CallServiceV1(CallServiceV1 callService) {
//        this.callService = callService;
//    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
