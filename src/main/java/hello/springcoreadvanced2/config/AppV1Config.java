package hello.springcoreadvanced2.config;

import hello.springcoreadvanced2.app.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 수동 빈 등록
 */
@Configuration
public class AppV1Config {

    /**
     * @return {@link OrderControllerV1Impl}
     */
    @Bean
    public OrderControllerV1 orderController() {
        return new OrderControllerV1Impl(orderService());
    }

    /**
     * @return {@link OrderServiceV1Impl}
     */
    @Bean
    public OrderServiceV1 orderService() {
        return new OrderServiceV1Impl(orderRepository());
    }

    /**
     * @return {@link OrderRepositoryV1Impl}
     */
    @Bean
    public OrderRepositoryV1 orderRepository() {
        return new OrderRepositoryV1Impl();
    }
}
