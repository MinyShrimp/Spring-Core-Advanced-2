package hello.springcoreadvanced2.trace.template;

import hello.springcoreadvanced2.trace.TraceStatus;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

/**
 * 템플릿 메서드 패턴<br>
 * - {@link LogTrace} 의존
 */
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {
    private final LogTrace logTrace;

    /**
     * 템플릿 메서드
     *
     * @param message 로그 메시지
     */
    public T execute(String message) {
        TraceStatus status = null;

        try {
            status = logTrace.begin(message);

            T result = this.call();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    /**
     * 비즈니스 로직
     */
    protected abstract T call();
}
