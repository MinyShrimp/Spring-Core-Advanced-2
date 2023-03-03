package hello.springcoreadvanced2.config.v5_autoproxy;

import hello.springcoreadvanced2.config.AppV1Config;
import hello.springcoreadvanced2.config.AppV2Config;
import hello.springcoreadvanced2.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link AnnotationAwareAspectJAutoProxyCreator} 사용<br>
 * - 어드바이저만 스프링 빈으로 등록하면 자동 프록시 생성기가 프록시를 생성해준다.
 *
 * @see AnnotationAwareAspectJAutoProxyCreator
 */
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    @Bean
    public Advisor advisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
