package hello.springcoreadvanced2.config.v3_proxyfactory;

import hello.springcoreadvanced2.app.v2.OrderControllerV2;
import hello.springcoreadvanced2.app.v2.OrderRepositoryV2;
import hello.springcoreadvanced2.app.v2.OrderServiceV2;
import hello.springcoreadvanced2.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ProxyFactory}를 이용하여 프록시를 생성하는 설정 파일
 */
@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    /**
     * {@link NameMatchMethodPointcut}, {@link LogTraceAdvice}를 이용한 Advisor 생성
     *
     * @return {@link DefaultPointcutAdvisor}
     */
    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     * {@link ProxyFactory}를 사용하여 프록시 생성
     *
     * @param target app.v2.*
     * @return CGLIB 프록시
     */
    private Object getProxy(Object target, LogTrace logTrace) {
        // 프록시 팩토리 생성
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvisor(getAdvisor(logTrace));

        // 프록시 획득
        Object proxy = factory.getProxy();
        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), target.getClass());

        return proxy;
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 target = new OrderControllerV2(orderServiceV2(logTrace));
        return (OrderControllerV2) getProxy(target, logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 target = new OrderServiceV2(orderRepositoryV2(logTrace));
        return (OrderServiceV2) getProxy(target, logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 target = new OrderRepositoryV2();
        return (OrderRepositoryV2) getProxy(target, logTrace);
    }
}
