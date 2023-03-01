package hello.springcoreadvanced2.pureproxy.proxy.code;

/**
 * 과목 Interface<br>
 * - 실제 구현체: {@link RealSubject}<br>
 * - 프록시 구현체: {@link CacheProxy}
 */
public interface Subject {

    /**
     * 실행을 원하는 메서드
     */
    String operation();
}
