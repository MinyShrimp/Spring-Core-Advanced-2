package hello.springcoreadvanced2.app.v1;

import hello.springcoreadvanced2.wrapper.SleepWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * v1<br>
 * 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록<br><br>
 * <p>
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
        SleepWrapper.sleep(1000);
    }
}
