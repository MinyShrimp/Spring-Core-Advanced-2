package hello.springcoreadvanced2.advisor;

import hello.springcoreadvanced2.common.service.ServiceImpl;
import hello.springcoreadvanced2.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class MultiAdvisorTest {

    /**
     * Client -> ( Proxy 2 -> Advisor ) -> ( Proxy 1 -> Advisor ) -> Target
     */
    @Test
    @DisplayName("프록시 체이닝 - 여러 프록시, 여러 어드바이저")
    void multiAdvisorTest1() {
        // 타겟 생성
        ServiceInterface target = new ServiceImpl();

        // 프록시 1 생성 -> 타겟
        ProxyFactory proxyFactory1 = new ProxyFactory(target);
        proxyFactory1.addAdvice(new Advice1());

        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        // 프록시 2 생성 -> 프록시 1
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        proxyFactory2.addAdvice(new Advice2());

        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        // 프록시 2 호출
        proxy2.save();
        proxy2.find();
    }

    /**
     * Client -> ( Proxy -> Advisor 2 -> Advisor 1 ) -> Target
     *
     * @see ProxyFactory#addAdvisor(Advisor)
     */
    @Test
    @DisplayName("프록시 체이닝 - 하나의 프록시, 여러 어드바이저")
    void multiAdvisorTest2() {
        // 타겟 생성
        ServiceInterface target = new ServiceImpl();

        // 여러 어드바이저 생성
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        // 프록시 생성 -> 어드바이저 2 -> 어드바이저 1 -> 타겟
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);

        // 프록시 획득
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // 프록시 호출
        proxy.save();
        proxy.find();
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice 1 호출");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice 2 호출");
            return invocation.proceed();
        }
    }
}
