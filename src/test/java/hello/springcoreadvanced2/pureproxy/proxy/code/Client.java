package hello.springcoreadvanced2.pureproxy.proxy.code;

import lombok.RequiredArgsConstructor;

/**
 * {@link Subject}를 의존하는 Client
 */
@RequiredArgsConstructor
public class Client {
    private final Subject subject;

    /**
     * {@link Subject#operation()} 호출
     */
    public void execute() {
        subject.operation();
    }
}
