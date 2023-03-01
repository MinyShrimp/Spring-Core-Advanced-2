package hello.springcoreadvanced2.app.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController 인터페이스<br>
 * - {@link OrderControllerV1Impl}<br>
 * - {@link Controller}, {@link RestController}가 없으면 인식 못함
 */
@RestController
@RequestMapping("/v1")
public interface OrderControllerV1 {

    /**
     * GET /v1/request
     *
     * @param itemId 상품 ID
     * @return ECHO 상품 ID
     */
    @GetMapping("/request")
    String request(@RequestParam("itemId") String itemId);

    /**
     * GET /v1/no-log
     *
     * @return "ok"
     */
    @GetMapping("/no-log")
    String noLog();
}
