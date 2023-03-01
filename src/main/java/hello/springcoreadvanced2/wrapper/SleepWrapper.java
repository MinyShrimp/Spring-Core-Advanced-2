package hello.springcoreadvanced2.wrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Thread#sleep}, {@link InterruptedException} Wrapper Class
 */
@Slf4j
public class SleepWrapper {

    private SleepWrapper() {
    }

    /**
     * {@link Thread#sleep}, {@link InterruptedException} Wrapper
     *
     * @param millis 중지할 시간
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.info("", e);
        }
    }
}
