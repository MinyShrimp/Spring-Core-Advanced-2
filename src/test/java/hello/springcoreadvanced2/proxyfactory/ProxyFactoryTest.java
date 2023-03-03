package hello.springcoreadvanced2.proxyfactory;

import hello.springcoreadvanced2.common.advice.TimeAdvice;
import hello.springcoreadvanced2.common.service.ConcreteService;
import hello.springcoreadvanced2.common.service.ServiceImpl;
import hello.springcoreadvanced2.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ProxyFactory} Test
 */
@Slf4j
public class ProxyFactoryTest {

    /**
     * {@link ProxyFactory} - JDK 동적 프록시 생성 테스트
     * <p>
     * 1. 프록시 팩토리 생성자에 타겟을 주입한다.<br>
     * 2. {@link ProxyFactory#addAdvice}에 핸들러({@link MethodInterceptor})를 주입한다.
     * 3. {@link ProxyFactory#getProxy}로 프록시를 꺼내온다.
     */
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    public void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리를 생성할 때 생성자에 타겟을 주입한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // addAdvice 메서드를 통해 어드바이스(프록시)를 주입한다.
        // "add" 이다. 이 말은 곧 여러 프록시를 등록할 수 있다는 뜻이다.
        proxyFactory.addAdvice(new TimeAdvice());

        // getProxy 메서드를 통해 프록시 팩토리의 프록시를 꺼내온다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // class hello.springcoreadvanced2.common.service.ServiceImpl
        log.info("targetClass = {}", target.getClass());
        // class jdk.proxy2.$Proxy9
        // 타겟이 Interface 이므로, JDK 동적 프록시를 사용한다.
        log.info("proxyClass = {}", proxy.getClass());

        // 프록시 호출
        proxy.save();

        // AopProxy 가 맞는지
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // JDK 동적 프록시가 맞는지
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        // CGLIB 프록시가 아닌지
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    /**
     * {@link ProxyFactory} - CGLIB 프록시 생성 테스트
     * <p>
     * 1. 생성자에 타겟을 주입하는 것은 동일하다.
     * 2. 핸들러를 주입하는 것도 동일하다.
     * 3. 프록시를 꺼내오는 것도 동일하다.
     */
    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    public void concreteProxy() {
        ConcreteService target = new ConcreteService();

        // 프록시 팩토리를 생성할 때 생성자에 타겟을 주입한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        // getProxy 메서드를 통해 프록시 팩토리의 프록시를 꺼내온다.
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        // class hello.springcoreadvanced2.common.service.ServiceImpl
        log.info("targetClass = {}", target.getClass());
        // class hello.springcoreadvanced2.common.service.ConcreteService$$SpringCGLIB$$0
        // 타겟이 구체 클래스 이므로, CGLIB 프록시를 사용한다.
        log.info("proxyClass = {}", proxy.getClass());

        // 프록시 호출
        proxy.call();

        // AopProxy 가 맞는지
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // JDK 동적 프록시가 아닌지
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        // CGLIB 프록시가 맞는지
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    /**
     * {@link ProxyFactory#setProxyTargetClass}에 true를 주면 무조건 CGLIB를 사용한다.
     */
    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용")
    public void proxyTargetProxy() {
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        // 이 옵션을 주면 인터페이스의 여부와는 상관없이 CGLIB를 사용하여 프록시를 생성한다.
        proxyFactory.setProxyTargetClass(true);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // class hello.springcoreadvanced2.common.service.ServiceImpl
        log.info("targetClass = {}", target.getClass());
        // class hello.springcoreadvanced2.common.service.ServiceImpl$$SpringCGLIB$$0
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
