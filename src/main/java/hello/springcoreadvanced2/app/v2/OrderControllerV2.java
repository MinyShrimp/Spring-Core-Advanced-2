package hello.springcoreadvanced2.app.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * v2<br>
 * 인터페이스 없는 구체 클래스 - 스프링 빈 수동 등록<br><br>
 * 주문 컨트롤러
 */
@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class OrderControllerV2 {
    private final OrderServiceV2 orderService;

    /**
     * GET /v2/request
     *
     * @param itemId 상품 ID
     * @return ECHO 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @GetMapping("/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return itemId;
    }

    /**
     * GET /v2/no-log
     *
     * @return "noLog ok"
     */
    @GetMapping("no-log")
    public String noLog() {
        return "noLog ok";
    }
}