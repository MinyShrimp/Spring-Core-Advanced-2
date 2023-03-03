package hello.springcoreadvanced2.config.v6_aop;

import hello.springcoreadvanced2.config.AppV1Config;
import hello.springcoreadvanced2.config.AppV2Config;
import hello.springcoreadvanced2.config.v6_aop.aspect.LogTraceAspect;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link Aspect}를 이용해서 생성된 어드바이저를 빈으로 등록하는 설정 파일
 */
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {
    
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
