package hello.springcoreadvanced2.config.v1_proxy;

import hello.springcoreadvanced2.app.v1.*;
import hello.springcoreadvanced2.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.springcoreadvanced2.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.springcoreadvanced2.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * V1 Proxy Configuration
 */
@Configuration
public class InterfaceProxyConfig {

    /**
     * @return {@link OrderControllerInterfaceProxy}
     */
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    /**
     * @return {@link OrderServiceInterfaceProxy}
     */
    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    /**
     * @return {@link OrderRepositoryInterfaceProxy}
     */
    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }
}
