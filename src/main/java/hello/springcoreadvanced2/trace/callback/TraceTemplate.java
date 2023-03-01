package hello.springcoreadvanced2.trace.callback;

import hello.springcoreadvanced2.trace.TraceStatus;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

/**
 * 템플릿 콜백 패턴 - 템플릿
 */
@RequiredArgsConstructor
public class TraceTemplate {

    private final LogTrace logTrace;

    /**
     * 템플릿 구현체
     *
     * @param message  로그 메시지
     * @param callback 비즈니스 로직
     */
    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;

        try {
            status = logTrace.begin(message);

            T result = callback.call();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
