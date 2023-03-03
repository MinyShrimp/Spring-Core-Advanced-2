package hello.springcoreadvanced2.config.v4_postprocessor;

import hello.springcoreadvanced2.config.AppV1Config;
import hello.springcoreadvanced2.config.AppV2Config;
import hello.springcoreadvanced2.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.springcoreadvanced2.config.v4_postprocessor.postprocessor.PackageLogTraceProxyPostProcessor;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    /**
     * 빈 후처리기 등록
     */
    @Bean
    public PackageLogTraceProxyPostProcessor logTraceProxyPostProcessor(
            LogTrace logTrace
    ) {
        return new PackageLogTraceProxyPostProcessor(
                "hello.springcoreadvanced2.app",
                getAdvisor(logTrace)
        );
    }

    private Advisor getAdvisor(
            LogTrace logTrace
    ) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
