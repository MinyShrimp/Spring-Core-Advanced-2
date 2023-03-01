package hello.springcoreadvanced2.pureproxy.concreteproxy;

import hello.springcoreadvanced2.pureproxy.concreteproxy.code.ConcreteClient;
import hello.springcoreadvanced2.pureproxy.concreteproxy.code.ConcreteLogic;
import org.junit.jupiter.api.Test;

/**
 * {@link ConcreteLogic} {@link ConcreteClient} Test
 */
public class ConcreteProxyTest {

    /**
     * 프록시가 적용되지 않은 평범한 방법
     */
    @Test
    void noProxy() {
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(logic);

        client.execute();
    }
}
