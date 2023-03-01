package hello.springcoreadvanced2.config.v1_proxy;

import hello.springcoreadvanced2.app.v2.OrderControllerV2;
import hello.springcoreadvanced2.app.v2.OrderRepositoryV2;
import hello.springcoreadvanced2.app.v2.OrderServiceV2;
import hello.springcoreadvanced2.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import hello.springcoreadvanced2.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import hello.springcoreadvanced2.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * V2 구체 클래스 Proxy 설정
 */
@Configuration
public class ConcreteProxyConfig {

    /**
     * @return {@link OrderControllerConcreteProxy}
     */
    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        return new OrderControllerConcreteProxy(
                new OrderControllerV2(orderServiceV2(logTrace)),
                logTrace
        );
    }

    /**
     * @return {@link OrderServiceConcreteProxy}
     */
    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        return new OrderServiceConcreteProxy(
                new OrderServiceV2(orderRepositoryV2(logTrace)),
                logTrace
        );
    }

    /**
     * @return {@link OrderRepositoryConcreteProxy}
     */
    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        return new OrderRepositoryConcreteProxy(
                new OrderRepositoryV2(), logTrace
        );
    }
}
