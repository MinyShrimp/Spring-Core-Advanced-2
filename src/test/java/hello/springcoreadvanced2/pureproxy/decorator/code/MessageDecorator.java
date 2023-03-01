package hello.springcoreadvanced2.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Decorator}를 상속받는 메시지 데코레이터
 */
@Slf4j
public class MessageDecorator extends Decorator {

    public MessageDecorator(Component target) {
        super(target);
    }

    /**
     * 메시지 데코레이터 실행
     *
     * @return "=== " + {@link Decorator#operation()} + " ===";
     */
    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = super.operation();
        String decoResult = "=== " + result + " ===";
        log.info("꾸미기 적용 전 = [{}], 적용 후 = [{}]", result, decoResult);

        return decoResult;
    }
}
