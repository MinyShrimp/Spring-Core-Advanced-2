package hello.springcoreadvanced2.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 실제 {@link Component} 구현체
 */
@Slf4j
public class RealComponent implements Component {

    /**
     * 실제로 실행되는 메서드
     *
     * @return "data"
     */
    @Override
    public String operation() {
        log.info("RealComponent 실행");
        return "data";
    }
}
