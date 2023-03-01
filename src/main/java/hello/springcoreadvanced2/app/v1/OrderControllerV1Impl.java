package hello.springcoreadvanced2.app.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link OrderControllerV1} 구현체<br>
 * - 의존: {@link OrderServiceV1}
 */
@Slf4j
@RequiredArgsConstructor
public class OrderControllerV1Impl implements OrderControllerV1 {
    private final OrderServiceV1 orderService;

    /**
     * GET /v1/request
     *
     * @param itemId 상품 ID
     * @return ECHO 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return itemId;
    }

    /**
     * GET /v1/no-log
     *
     * @return "noLog ok"
     */
    @Override
    public String noLog() {
        return "noLog ok";
    }
}
