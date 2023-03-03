# @Aspect AOP

## @Aspect 프록시 - 적용

### AnnotationAwareAspectJAutoProxyCreator

스프링 애플리케이션에 프록시를 적용하려면
포인트컷과 어드바이스로 구성되어 있는 어드바이저(`Advisor`)를 만들어서 스프링 빈으로 등록하면 된다.

그러면 나머지는 앞서 배운 자동 프록시 생성기가 모두 자동으로 처리해준다.

자동 프록시 생성기는 스프링 빈으로 등록된 어드바이저들을 찾고, 스프링 빈들에 자동으로 프록시를 적용해준다.
(물론 포인트컷이 매칭되는 경우에 프록시를 생성한다.)

### @Aspect

스프링은 `@Aspect` 애노테이션으로 매우 편리하게
포인트컷과 어드바이스로 구성되어 있는 **어드바이저 생성 기능**을 지원한다.

#### 참고

`@Aspect`는 관점 지향 프로그래밍(AOP)을 가능하게 하는 `AspectJ` 프로젝트에서 제공하는 애노테이션이다.
스프링은 이것을 차용해서 프록시를 통한 AOP를 가능하게 한다.

AOP와 `AspectJ` 관련된 자세한 내용은 다음에 설명한다. 지금은 프록시에 초점을 맞추자.
우선 이 애노테이션을 사용해서 스프링이 편리하게 프록시를 만들어준다고 생각하면 된다.

### 예제

#### LogTraceAspect

```java
/**
 * {@link Aspect} 애노테이션을 이용해 어드바이저 생성
 *
 * @see Aspect
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {
    private final LogTrace logTrace;

    /**
     * {@link Around} 애노테이션을 이용해서 포인트컷과 어드바이스를 동시에 생성<br>
     * - AspectJ 표현식
     *
     * @see Around
     * @see ProceedingJoinPoint
     * @see Signature
     */
    @Around("execution(* hello.springcoreadvanced2.app..*(..))")
    public Object execute(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {
        TraceStatus status = null;

        log.info("[      target] = {}", joinPoint.getTarget());    // 실제 호출 대상
        log.info("[     getArgs] = {}", joinPoint.getArgs());      // 전달 인자
        log.info("[getSignature] = {}", joinPoint.getSignature()); // Method 와 비슷한것

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
```

#### AopConfig

```java
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
```

* `@Bean logTraceAspect()`
    * `@Aspect`가 있어도 스프링 빈으로 등록을 해줘야 한다.
    * 물론 `LogTraceAspect`에 `@Component` 애노테이션을 붙여서 컴포넌트 스캔을 사용해서 스프링 빈으로 등록해도 된다.

#### ProxyApplication

```java
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
```

## @Aspect 프록시 - 설명
