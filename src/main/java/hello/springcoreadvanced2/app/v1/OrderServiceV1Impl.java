package hello.springcoreadvanced2.app.v1;

import lombok.RequiredArgsConstructor;

/**
 * {@link OrderServiceV1} 구현체
 */
@RequiredArgsConstructor
public class OrderServiceV1Impl implements OrderServiceV1 {
    private final OrderRepositoryV1 orderRepository;

    /**
     * 상품 저장 비즈니스 로직
     *
     * @param itemId 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
