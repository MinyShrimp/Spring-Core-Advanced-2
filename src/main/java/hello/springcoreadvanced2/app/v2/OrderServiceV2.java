package hello.springcoreadvanced2.app.v2;

import lombok.RequiredArgsConstructor;

/**
 * v2<br>
 * 인터페이스 없는 구체 클래스 - 스프링 빈 수동 등록<br><br>
 * 주문 서비스
 */
@RequiredArgsConstructor
public class OrderServiceV2 {
    private final OrderRepositoryV2 orderRepository;

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
