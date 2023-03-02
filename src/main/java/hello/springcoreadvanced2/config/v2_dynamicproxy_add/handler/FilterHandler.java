package hello.springcoreadvanced2.config.v2_dynamicproxy_add.handler;

import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 타겟의 메서드 이름을 필터링하는 Handler<br>
 * {@link PatternMatchUtils#simpleMatch}를 이용하여 패턴 검증<br>
 * - 해당 메서드의 이름이 패턴과 일치한다면: {@link #nextHandler}<br>
 * - 해당 메서드의 이름이 패턴과 일치하지 않는다면: {@link #target}
 *
 * @author MinyShrimp
 * @see Proxy
 * @see InvocationHandler
 * @see PatternMatchUtils#simpleMatch(String[], String)
 * @since 2023-03-02
 */
public class FilterHandler implements InvocationHandler {
    private final Object target;
    private final Object nextHandler;
    private final String[] methodPatterns;

    /**
     * @param target         최종 목표 구현체
     * @param nextHandler    다음 ProxyHandler
     * @param methodPatterns 필터링을 원하는 패턴 목록 - {@link PatternMatchUtils#simpleMatch}
     */
    public FilterHandler(
            Object target,
            Object nextHandler,
            String[] methodPatterns
    ) {
        this.target = target;
        this.nextHandler = nextHandler;
        this.methodPatterns = methodPatterns;
    }

    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) throws Throwable {

        Object next = PatternMatchUtils.simpleMatch(methodPatterns, method.getName())
                ? nextHandler
                : target;

        return method.invoke(next, args);
    }
}
