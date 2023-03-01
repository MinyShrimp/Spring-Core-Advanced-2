package hello.springcoreadvanced2.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 구체 로직 클래스 <br>
 * {@link ConcreteClient}이 의존하고 있다.
 */
@Slf4j
public class ConcreteLogic {
    public String operation() {
        log.info("ConcreteLogic 실행");
        return "data";
    }
}
