package hello.springcoreadvanced2.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * v3<br>
 * 컴포넌트 스캔으로 스프링 빈 자동 등록<br><br>
 * 주문 컨트롤러
 */
@RestController
@RequestMapping("/v3")
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final OrderServiceV3 orderService;

    /**
     * GET /v3/request
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
     * GET /v3/no-log
     *
     * @return "noLog ok"
     */
    @GetMapping("no-log")
    public String noLog() {
        return "noLog ok";
    }
}