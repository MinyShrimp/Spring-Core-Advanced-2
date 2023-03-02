package hello.springcoreadvanced2.config.v2_dynamicproxy.handler;

import hello.springcoreadvanced2.trace.TraceStatus;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 동적 프록시 사용<br>
 * - {@link InvocationHandler} JDK 동적 프록시에 로직을 적용하기 위한 Handler<br>
 * - {@link PatternMatchUtils#simpleMatch}로 WhiteList 기반 URL 패턴 필터링
 */
@Slf4j
@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {
    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) throws Throwable {

        // patterns 에 해당 메서드 이름이 없다면,
        if (!PatternMatchUtils.simpleMatch(patterns, method.getName())) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        try {
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);

            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
