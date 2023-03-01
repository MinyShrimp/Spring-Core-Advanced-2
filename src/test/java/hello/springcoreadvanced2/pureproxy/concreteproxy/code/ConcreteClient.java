package hello.springcoreadvanced2.pureproxy.concreteproxy.code;

import lombok.RequiredArgsConstructor;

/**
 * 구체 클라이언트 클래스<br>
 * {@link ConcreteLogic} 의존
 */
@RequiredArgsConstructor
public class ConcreteClient {
    private final ConcreteLogic concreteLogic;

    public void execute() {
        concreteLogic.operation();
    }
}
