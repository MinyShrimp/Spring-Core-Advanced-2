package hello.springcoreadvanced2.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link Component}를 상속받는 시간 Decorator
 */
@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component {
    private final Component component;

    /**
     * 현재 {@link Component} Decorator 부터 하위 Decorator 까지의 총 소모 시간을 구하는 메서드
     *
     * @return 이후 {@link Component} 체이닝의 총 소모 시간
     */
    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();

        String result = component.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime = [{}ms]", resultTime);

        return result;
    }
}
