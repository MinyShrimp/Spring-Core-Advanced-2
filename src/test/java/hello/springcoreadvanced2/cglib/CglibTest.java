package hello.springcoreadvanced2.cglib;

import hello.springcoreadvanced2.cglib.code.TimeMethodInterceptor;
import hello.springcoreadvanced2.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 * CGLIB 테스트<br>
 * - CGLIB는 구체 클래스를 "상속"하여 프록시를 생성한다.
 */
@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        // CGLIB에서 제공하는 프록시 생성기
        Enhancer enhancer = new Enhancer();
        // 프록시를 생성할 구체 클래스 지정
        enhancer.setSuperclass(ConcreteService.class);
        // 프록시에 실행할 로직을 할당한다.
        enhancer.setCallback(new TimeMethodInterceptor(target));
        // 프록시를 생성한다.
        ConcreteService proxy = (ConcreteService) enhancer.create();

        // class hello.springcoreadvanced2.common.service.ConcreteService
        log.info("targetClass = {}", target.getClass());

        // class hello.springcoreadvanced2.common.service.ConcreteService$$EnhancerByCGLIB$$e30fa261
        log.info("proxyClass = {}", proxy.getClass());

        // 위에서 생성한 프록시를 호출한다.
        proxy.call();
    }
}
