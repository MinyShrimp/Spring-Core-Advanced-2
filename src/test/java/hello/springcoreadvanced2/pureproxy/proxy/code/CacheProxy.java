package hello.springcoreadvanced2.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 캐싱 프록시 객체
 */
@Slf4j
public class CacheProxy implements Subject {

    /**
     * 목표: {@link Subject} 구현체
     */
    private final Subject target;

    /**
     * 캐시값
     * - 초기값: null
     */
    private String cacheValue;

    /**
     * @param target 목표 Subject
     */
    public CacheProxy(Subject target) {
        this.target = target;
    }

    /**
     * 프록시 객체가 {@link #target}을 대신 호출해줌.<br>
     * {@link #target}의 값을 Caching 한다.<br><br>
     *
     * <code>cacheValue == null: {@link #target}.operation()</code><br>
     * <code>cacheValue != null: {@link #cacheValue}</code>
     */
    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}
