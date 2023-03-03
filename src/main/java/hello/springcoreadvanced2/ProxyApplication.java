package hello.springcoreadvanced2;

import hello.springcoreadvanced2.config.v1_proxy.ConcreteProxyConfig;
import hello.springcoreadvanced2.config.v3_proxyfactory.ProxyFactoryConfigV1;
import hello.springcoreadvanced2.trace.logtrace.LogTraceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        LogTraceConfig.class,
        ProxyFactoryConfigV1.class,
        ConcreteProxyConfig.class
})
@SpringBootApplication(scanBasePackages = "hello.springcoreadvanced2.app.v3")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }
}
