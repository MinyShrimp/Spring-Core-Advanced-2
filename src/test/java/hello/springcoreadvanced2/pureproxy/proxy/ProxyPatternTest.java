package hello.springcoreadvanced2.pureproxy.proxy;

import hello.springcoreadvanced2.pureproxy.proxy.code.CacheProxy;
import hello.springcoreadvanced2.pureproxy.proxy.code.Client;
import hello.springcoreadvanced2.pureproxy.proxy.code.RealSubject;
import hello.springcoreadvanced2.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.Test;

/**
 * {@link Subject}, {@link RealSubject}, {@link CacheProxy}, {@link Client} Test
 */
public class ProxyPatternTest {

    /**
     * {@link Client}가 3번 호출되면서 {@link RealSubject}또한 단순히 3번 호출이 된다.
     * <p>
     * {@link RealSubject}는 호출당 1초씩 걸리기 때문에 총 소요시간은 [3초]가 된다.
     */
    @Test
    void noProxyTest() {
        Subject realSubject = new RealSubject();
        Client client = new Client(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }

    /**
     * {@link Client}가 3번 호출되지만,
     * {@link RealSubject}가 아닌 {@link CacheProxy}가 대신 호출됨.
     * <p>
     * {@link CacheProxy}는 최초 한 번 {@link RealSubject}을 호출하고,
     * 그 이후에는 캐싱된 {@link RealSubject}의 결과값을 반환.
     * <p>
     * 결과적으로 총 [1 ~ 1.5초]의 시간이 소모된다.
     */
    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        Client client = new Client(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
