package hello.springcoreadvanced2.pureproxy.concreteproxy;

import hello.springcoreadvanced2.pureproxy.concreteproxy.code.ConcreteClient;
import hello.springcoreadvanced2.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.springcoreadvanced2.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

/**
 * {@link ConcreteLogic} {@link TimeProxy} {@link ConcreteClient} Test
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

    /**
     * 구체 클래스를 상속받은 프록시를 전달
     */
    @Test
    void addProxy() {
        ConcreteLogic logic = new ConcreteLogic();
        TimeProxy proxy = new TimeProxy(logic);
        ConcreteClient client = new ConcreteClient(proxy);

        client.execute();
    }
}
