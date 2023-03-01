package hello.springcoreadvanced2.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Decorator}를 상속받는 시간 Decorator
 */
@Slf4j
public class TimeDecorator extends Decorator {

    public TimeDecorator(Component target) {
        super(target);
    }

    /**
     * 현재 {@link Decorator}부터 하위 Decorator 까지의 총 소모 시간을 구하는 메서드
     *
     * @return 이후 {@link Decorator} 체이닝의 총 소모 시간
     */
    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();

        String result = super.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime = [{}ms]", resultTime);

        return result;
    }
}
