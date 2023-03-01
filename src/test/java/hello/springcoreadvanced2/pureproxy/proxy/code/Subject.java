package hello.springcoreadvanced2.pureproxy.proxy.code;

/**
 * 프록시 객체<br>
 * - 구현체: {@link RealSubject}
 */
public interface Subject {

    /**
     * 실제로 실행되는 메서드
     */
    String operation();
}
