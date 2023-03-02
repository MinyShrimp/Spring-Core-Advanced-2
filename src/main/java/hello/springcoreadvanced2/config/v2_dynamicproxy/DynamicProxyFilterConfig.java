package hello.springcoreadvanced2.config.v2_dynamicproxy;

import hello.springcoreadvanced2.app.v1.*;
import hello.springcoreadvanced2.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Proxy;

/**
 * JDK 동적 프록시를 스프링 빈으로 등록하는 설정 클래스<br>
 * - {@link LogTraceFilterHandler} 적용
 */
@Configuration
public class DynamicProxyFilterConfig {

    /**
     * 적용할 URL Whitelist 목록<br>
     * - {@link PatternMatchUtils#simpleMatch} 사용
     */
    private static final String[] PATTERNS = {
            "request*", "order*", "save*"
    };

    /**
     * @return {@link OrderControllerV1}의 JDK 동적 {@link Proxy}
     */
    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {

        return (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceFilterHandler(
                        new OrderControllerV1Impl(orderServiceV1(logTrace)),
                        logTrace, PATTERNS
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
                new LogTraceFilterHandler(
                        new OrderServiceV1Impl(orderRepositoryV1(logTrace)),
                        logTrace, PATTERNS
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
                new LogTraceFilterHandler(new OrderRepositoryV1Impl(), logTrace, PATTERNS)
        );
    }
}
