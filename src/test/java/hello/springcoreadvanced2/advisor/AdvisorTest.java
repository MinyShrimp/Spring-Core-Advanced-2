package hello.springcoreadvanced2.advisor;

import hello.springcoreadvanced2.common.advice.TimeAdvice;
import hello.springcoreadvanced2.common.service.ServiceImpl;
import hello.springcoreadvanced2.common.service.ServiceInterface;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * {@link Advisor} Test
 */
public class AdvisorTest {

    /**
     * {@link Advisor} Test<br>
     * - {@link Pointcut#TRUE}:                    항상 True를 반환하는 포인트 컷<br>
     * - {@link DefaultPointcutAdvisor}:           {@link Advisor}의 일반적인 구현체<br>
     * - {@link ProxyFactory#addAdvisor(Advisor)}: 프록시 팩토리에 어드바이저 추가<br>
     * - {@link ProxyFactory#addAdvice(Advice)}:   내부에서 {@link Pointcut#TRUE}인 어드바이저를 생성
     *
     * @see Pointcut#TRUE
     * @see DefaultPointcutAdvisor
     * @see ProxyFactory#addAdvice(Advice)
     * @see ProxyFactory#addAdvisor(Advisor)
     */
    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 어드바이저 생성
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        // 어드바이저 추가
        proxyFactory.addAdvisor(advisor);

        // 프록시 획득
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}
