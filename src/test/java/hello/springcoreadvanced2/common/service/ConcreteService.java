package hello.springcoreadvanced2.common.service;

import lombok.extern.slf4j.Slf4j;

/**
 * CGLIB 예제를 위한 서비스 구현체<br>
 * - 인터페이스를 상속받지 않은 클래스이다.
 */
@Slf4j
public class ConcreteService {

    /**
     * 호출
     */
    public void call() {
        log.info("ConcreteService 호출");
    }
}
