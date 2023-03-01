package hello.springcoreadvanced2.pureproxy.proxy.code;

import lombok.RequiredArgsConstructor;

/**
 * 프록시를 호출하는 Client<br>
 * - 의존: {@link Subject}
 */
@RequiredArgsConstructor
public class ProxyPatternClient {
    private final Subject subject;

    /**
     * 프록시 호출
     */
    public void execute() {
        subject.operation();
    }
}
