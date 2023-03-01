package hello.springcoreadvanced2.app.v1;

/**
 * OrderService 인터페이스<br>
 * - {@link OrderServiceV1Impl}
 */
public interface OrderServiceV1 {

    /**
     * 상품 주문 비즈니스 로직
     *
     * @param itemId 상품 ID
     */
    void orderItem(String itemId);
}
