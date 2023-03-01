package hello.springcoreadvanced2.trace.callback;

/**
 * 템플릿 콜백 패턴 - 콜백
 */
@FunctionalInterface
public interface TraceCallback<T> {

    /**
     * 로직
     */
    T call();
}
