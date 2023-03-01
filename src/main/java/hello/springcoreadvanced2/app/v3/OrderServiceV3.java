package hello.springcoreadvanced2.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * v3<br>
 * 컴포넌트 스캔으로 스프링 빈 자동 등록<br><br>
 * 주문 서비스
 */
@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
    private final OrderRepositoryV3 orderRepository;

    /**
     * 주문 저장 비즈니스 로직
     *
     * @param itemId 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
