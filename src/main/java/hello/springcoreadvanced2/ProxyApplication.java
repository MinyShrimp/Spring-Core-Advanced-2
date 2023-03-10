package hello.springcoreadvanced2;

import hello.springcoreadvanced2.config.v6_aop.AopConfig;
import hello.springcoreadvanced2.trace.logtrace.LogTraceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        LogTraceConfig.class,
        AopConfig.class
})
@SpringBootApplication(scanBasePackages = "hello.springcoreadvanced2.app.v3")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }
}
