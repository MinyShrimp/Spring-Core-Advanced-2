package hello.springcoreadvanced2.config.v2_dynamicproxy.handler;

import hello.springcoreadvanced2.app.v1.OrderControllerV1;
import hello.springcoreadvanced2.app.v1.OrderControllerV1Impl;
import hello.springcoreadvanced2.trace.TraceStatus;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 동적 프록시 사용<br>
 * - {@link InvocationHandler} JDK 동적 프록시에 로직을 적용하기 위한 Handler
 * - 이 핸들러는 {@link LogTrace}를 사용하기 위한 프록시 핸들러이다.
 */
@Slf4j
@RequiredArgsConstructor
public class LogTraceBasicHandler implements InvocationHandler {
    private final Object target;
    private final LogTrace logTrace;

    /**
     * @param proxy  프록시 자신, 여기서는 {@link #target}과 같다. - {@link OrderControllerV1Impl}
     * @param method 인터페이스의 메서드, 여기서는 {@link OrderControllerV1#request}이다.
     * @param args   메서드를 호출할 때 전달할 인수
     */
    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) throws Throwable {

        TraceStatus status = null;

        try {

            /**
             * {@link Method#getClass()}           {@link Method}
             * {@link Method#getDeclaringClass()}  원본 {@link Class}, {@link OrderControllerV1}
             * {@link Class#getSimpleName()}       클래스의 이름, OrderControllerV1
             * {@link Method#getName()}            메서드의 이름, request
             */
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);

            /**
             * {@link Method#invoke(Object, Object...)}
             *
             * @param Object    실행을 원하는 인스턴스, {@link #target}
             * @param Object... 해당 메서드를 실행하기 위해 필요한 파라미터, args
             */
            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
