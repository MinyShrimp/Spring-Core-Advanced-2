package hello.springcoreadvanced2.pureproxy.decorator.code;

/**
 * Component 인터페이스<br>
 * - 구현체: {@link RealComponent}
 */
public interface Component {

    /**
     * 실행을 원하는 메서드
     */
    String operation();
}
