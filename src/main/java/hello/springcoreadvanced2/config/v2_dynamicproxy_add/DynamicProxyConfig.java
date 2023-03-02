package hello.springcoreadvanced2.config.v2_dynamicproxy_add;

import hello.springcoreadvanced2.app.v1.*;
import hello.springcoreadvanced2.config.v2_dynamicproxy_add.handler.FilterHandler;
import hello.springcoreadvanced2.config.v2_dynamicproxy_add.handler.LogTraceHandler;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

/**
 * JDK 동적 {@link Proxy}를 스프링 빈으로 등록하기 위한 설정 파일
 *
 * @author MinyShrimp
 * @see Proxy
 * @see FilterHandler
 * @see LogTraceHandler
 * @since 2023-03-02
 */
@Slf4j
@Configuration
public class DynamicProxyConfig {

    /**
     * {@link FilterHandler}에서 사용하는 필터링 조건들, Whitelist 방식.
     */
    private static final String[] METHOD_PATTERNS = {
            "request*", "order*", "save*"
    };

    /**
     * 이 메서드를 사용하기 위해선 FilterHandler와 LogTraceHandler는
     * target의 첫 번째 인터페이스를 기반으로 프록시를 생성할 수 있어야 한다.
     *
     * @param target   최종 목표 구현체, 예) {@link OrderControllerV1Impl}
     * @param logTrace {@link LogTrace}
     * @return {@link FilterHandler} -> {@link LogTraceHandler} -> {@link OrderControllerV1Impl}
     */
    private static Object filterLogProxyFactory(
            Object target,
            LogTrace logTrace
    ) {
        // 타겟이 상속받은 인터페이스들을 가져온다.
        Class<?>[] supIntfs = target.getClass().getInterfaces();

        // LogTraceProxy 생성
        Object logTraceProxy = Proxy.newProxyInstance(
                supIntfs[0].getClassLoader(),
                supIntfs,
                new LogTraceHandler(target, logTrace)
        );

        // LogTraceProxy, 목표 타겟을 담은 FilterProxy 생성
        return Proxy.newProxyInstance(
                supIntfs[0].getClassLoader(),
                supIntfs,
                new FilterHandler(target, logTraceProxy, METHOD_PATTERNS)
        );
    }

    /**
     * @return {@link OrderControllerV1Impl}의 Proxy
     */
    @Bean
    OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1Impl target = new OrderControllerV1Impl(orderServiceV1(logTrace));
        return (OrderControllerV1) filterLogProxyFactory(target, logTrace);
    }

    /**
     * @return {@link OrderServiceV1Impl}의 Proxy
     */
    @Bean
    OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        return (OrderServiceV1) filterLogProxyFactory(target, logTrace);
    }

    /**
     * @return {@link OrderRepositoryV1Impl}의 Proxy
     */
    @Bean
    OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1Impl target = new OrderRepositoryV1Impl();
        return (OrderRepositoryV1) filterLogProxyFactory(target, logTrace);
    }
}
