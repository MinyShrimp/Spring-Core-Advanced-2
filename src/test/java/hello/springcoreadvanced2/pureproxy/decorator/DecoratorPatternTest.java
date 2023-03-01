package hello.springcoreadvanced2.pureproxy.decorator;

import hello.springcoreadvanced2.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * {@link Component}, {@link RealComponent} <br>
 * {@link MessageDecorator}, {@link TimeDecorator}, {@link Client}
 */
@Slf4j
public class DecoratorPatternTest {

    /**
     * 일반적인 호출
     * <p>
     * 결과로 {@link RealComponent#operation()}이 호출된다.
     */
    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        Client client = new Client(realComponent);

        client.execute();
    }

    /**
     * 메시지 데코레이터 호출
     * <p>
     * 결과로 {@link MessageDecorator#operation()}이 호출된다.
     */
    @Test
    void decorator1() {
        Component realComponent = new RealComponent();
        Component decorator = new MessageDecorator(realComponent);
        Client client = new Client(decorator);

        client.execute();
    }

    /**
     * 데코레이터 체이닝
     * <p>
     * {@link Client} -> {@link TimeDecorator} -> {@link MessageDecorator} -> {@link RealComponent}
     */
    @Test
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        Client client = new Client(timeDecorator);

        client.execute();
    }
}
