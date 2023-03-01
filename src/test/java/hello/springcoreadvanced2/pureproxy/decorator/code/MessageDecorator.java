package hello.springcoreadvanced2.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link Component}를 상속받는 메시지 데코레이터
 */
@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component {

    /**
     * 목표 {@link Component} 구현체
     */
    private final Component target;

    /**
     * 메시지 데코레이터 실행
     *
     * @return "=== " + {@link #target}.operation() + " ===";
     */
    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = target.operation();
        String decoResult = "=== " + result + " ===";
        log.info("꾸미기 적용 전 = [{}], 적용 후 = [{}]", result, decoResult);

        return decoResult;
    }
}
