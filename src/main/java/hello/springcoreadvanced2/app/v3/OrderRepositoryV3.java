package hello.springcoreadvanced2.app.v3;

import hello.springcoreadvanced2.wrapper.SleepWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * v3<br>
 * 컴포넌트 스캔으로 스프링 빈 자동 등록<br><br>
 * 주문 저장소
 */
@Slf4j
@Repository
public class OrderRepositoryV3 {

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
