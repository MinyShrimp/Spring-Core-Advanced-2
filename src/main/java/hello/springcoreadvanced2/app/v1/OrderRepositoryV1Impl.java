package hello.springcoreadvanced2.app.v1;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link OrderRepositoryV1} 구현체
 */
@Slf4j
public class OrderRepositoryV1Impl implements OrderRepositoryV1 {

    /**
     * 상품 저장 로직
     *
     * @param itemId 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @Override
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    /**
     * {@link Thread#sleep}, {@link InterruptedException} Wrapper
     *
     * @param millis 중지할 시간
     */
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.info("", e);
        }
    }
}
