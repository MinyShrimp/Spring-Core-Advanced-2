package hello.springcoreadvanced2.app.v2;

import hello.springcoreadvanced2.wrapper.SleepWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * v2<br>
 * 인터페이스 없는 구체 클래스 - 스프링 빈 수동 등록<br><br>
 * 주문 저장소
 */
@Slf4j
public class OrderRepositoryV2 {

    /**
     * 주문 저장 로직
     *
     * @param itemId 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        SleepWrapper.sleep(1000);
    }
}
