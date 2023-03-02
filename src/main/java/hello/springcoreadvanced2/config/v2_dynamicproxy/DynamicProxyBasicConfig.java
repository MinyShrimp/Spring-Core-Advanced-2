package hello.springcoreadvanced2.config.v2_dynamicproxy;

import hello.springcoreadvanced2.app.v1.*;
import hello.springcoreadvanced2.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK 동적 프록시를 스프링 빈으로 등록하는 설정 클래스
 */
@Configuration
public class DynamicProxyBasicConfig {

    /**
     * @return {@link OrderControllerV1}의 JDK 동적 {@link Proxy}
     */
    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {

        /**
         * {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}<br>
         * JDK 동적 프록시 생성
         * @param ClassLoader       ".class" 파일을 찾아주는 역할<br>
         * @param Class[]           구현을 원하는 Interface<br>
         * @param InvocationHandler 실제 구현 로직 Handler<br>
         */
        return (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceBasicHandler(
                        new OrderControllerV1Impl(orderServiceV1(logTrace)),
                        logTrace
                )
        );
    }

    /**
     * @return {@link OrderServiceV1}의 JDK 동적 {@link Proxy}
     */
    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        return (OrderServiceV1) Proxy.newProxyInstance(
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceBasicHandler(
                        new OrderServiceV1Impl(orderRepositoryV1(logTrace)),
                        logTrace
                )
        );
    }

    /**
     * @return {@link OrderRepositoryV1}의 JDK 동적 {@link Proxy}
     */
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        return (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceBasicHandler(new OrderRepositoryV1Impl(), logTrace)
        );
    }
}
