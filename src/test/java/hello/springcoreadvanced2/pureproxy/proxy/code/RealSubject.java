package hello.springcoreadvanced2.pureproxy.proxy.code;

import hello.springcoreadvanced2.wrapper.SleepWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link Subject}의 실제 구현체
 */
@Slf4j
public class RealSubject implements Subject {

    /**
     * 실제 객체 호출<br>
     * - <code>Thread.sleep(1000)</code>
     */
    @Override
    public String operation() {
        log.info("실제 객체 호출");
        SleepWrapper.sleep(1000);
        return "data";
    }
}
