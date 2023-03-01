package hello.springcoreadvanced2.config;

import hello.springcoreadvanced2.app.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * v1<br>
 * 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록<br><br>
 * <p>
 * 스프링 빈 수동 등록 설정
 */
@Configuration
public class AppV1Config {

    /**
     * @return {@link OrderControllerV1Impl}
     */
    @Bean
    public OrderControllerV1 orderControllerV1() {
        return new OrderControllerV1Impl(orderServiceV1());
    }

    /**
     * @return {@link OrderServiceV1Impl}
     */
    @Bean
    public OrderServiceV1 orderServiceV1() {
        return new OrderServiceV1Impl(orderRepositoryV1());
    }

    /**
     * @return {@link OrderRepositoryV1Impl}
     */
    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        return new OrderRepositoryV1Impl();
    }
}
