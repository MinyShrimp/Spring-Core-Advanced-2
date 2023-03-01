package hello.springcoreadvanced2.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link Component}를 사용하는 Client
 */
@Slf4j
@RequiredArgsConstructor
public class Client {
    private final Component component;

    /**
     * Client 실행
     * <p>
     * {@link Component#operation()}의 반환값을 받아서 화면에 출력한다.
     */
    public void execute() {
        String result = component.operation();
        log.info("result = {}", result);
    }
}
