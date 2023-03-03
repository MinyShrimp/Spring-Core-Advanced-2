package hello.springcoreadvanced2.advisor;

import hello.springcoreadvanced2.common.advice.TimeAdvice;
import hello.springcoreadvanced2.common.service.ServiceImpl;
import hello.springcoreadvanced2.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;

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

    /**
     * {@link MyPointcut} Test
     */
    @Test
    @DisplayName("직접 만든 포인트 컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 어드바이저 생성
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

        // 어드바이저 추가
        proxyFactory.addAdvisor(advisor);

        // 프록시 획득
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    /**
     * 포인트 컷 직접 구현
     *
     * @see Pointcut
     * @see ClassFilter
     * @see MethodMatcher
     */
    static class MyPointcut implements Pointcut {

        /**
         * 클래스 필터
         *
         * @return {@link ClassFilter#TRUE} 클래스 필터는 항상 통과한다.
         */
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        /**
         * 메서드 필터
         *
         * @return {@link MyMethodMatcher}
         */
        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    /**
     * 메서드 이름을 기반으로 검증
     * - method.getName().equals("save")
     *
     * @see MethodMatcher
     */
    @Slf4j
    static class MyMethodMatcher implements MethodMatcher {
        private final String matchName = "save";

        /**
         * {@link MethodMatcher#isRuntime()} is false
         *
         * @param method      Reflection Method
         * @param targetClass 목표 오리지널 인스턴스
         * @return method.getName().equals(" save ")
         */
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트 컷 호출 method = {}, targetClass = {}", method.getName(), targetClass);
            log.info("포인트 컷 결과 result = {}", result);
            return result;
        }

        /**
         * true: None Caching <br>
         * -> {@link MethodMatcher#matches(Method, Class, Object...)}<br>
         * false: Caching <br>
         * -> {@link MethodMatcher#matches(Method, Class)}
         *
         * @return false
         */
        @Override
        public boolean isRuntime() {
            return false;
        }

        /**
         * {@link MethodMatcher#isRuntime()} is true
         *
         * @param method      Reflection Method
         * @param targetClass 목표 오리지널 인스턴스
         * @param args        동적으로 넘어오는 파라미터
         * @return false
         */
        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
