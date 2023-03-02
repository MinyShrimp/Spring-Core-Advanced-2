package hello.springcoreadvanced2.config.v2_dynamicproxy_add.handler;

import hello.springcoreadvanced2.trace.TraceStatus;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import hello.springcoreadvanced2.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Logging Handler<br>
 * {@link LogTrace}를 이용하여 로그 출력
 *
 * @author MinyShrimp
 * @see Proxy
 * @see InvocationHandler
 * @see LogTrace
 * @see ThreadLocalLogTrace
 * @since 2023-03-02
 */
@Slf4j
public class LogTraceHandler implements InvocationHandler {
    private final Object target;
    private final LogTrace logTrace;
    private final ThreadLocal<TraceStatus> thStatus = new ThreadLocal<>();

    /**
     * @param target   목표 구현체, 다음 ProxyHandler
     * @param logTrace {@link LogTrace} 구현체
     */
    public LogTraceHandler(
            Object target,
            LogTrace logTrace
    ) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) throws Throwable {

        try {
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            TraceStatus status = logTrace.begin(message);
            thStatus.set(status);

            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            TraceStatus status = thStatus.get();
            logTrace.exception(status, e);
            throw e;
        } finally {
            if (logTrace.isFirstLevel()) {
                thStatus.remove();
            }
        }
    }
}
