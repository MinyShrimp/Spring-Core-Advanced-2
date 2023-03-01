package hello.springcoreadvanced2.config;

import hello.springcoreadvanced2.app.v2.OrderControllerV2;
import hello.springcoreadvanced2.app.v2.OrderRepositoryV2;
import hello.springcoreadvanced2.app.v2.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * v2<br>
 * 인터페이스 없는 구체 클래스 - 스프링 빈 수동 등록<br><br>
 * 스프링 빈 수동 등록 설정
 */
@Configuration
public class AppV2Config {

    @Bean
    public OrderControllerV2 orderControllerV2() {
        return new OrderControllerV2(orderServiceV2());
    }

    @Bean
    public OrderServiceV2 orderServiceV2() {
        return new OrderServiceV2(orderRepositoryV2());
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2() {
        return new OrderRepositoryV2();
    }
}