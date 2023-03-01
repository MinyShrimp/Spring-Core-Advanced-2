package hello.springcoreadvanced2.pureproxy.proxy;

import hello.springcoreadvanced2.pureproxy.proxy.code.ProxyPatternClient;
import hello.springcoreadvanced2.pureproxy.proxy.code.RealSubject;
import hello.springcoreadvanced2.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.Test;

/**
 * {@link Subject}, {@link RealSubject}, {@link ProxyPatternClient} Test
 */
public class ProxyPatternTest {

    /**
     * 프록시 클라이언트를 단순히 3번 호출한다.
     * <p>
     * RealSubject는 호출당 1초씩 걸리기 때문에 총 소요시간은 3초가 된다.
     */
    @Test
    void noProxyTest() {
        Subject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }
}
