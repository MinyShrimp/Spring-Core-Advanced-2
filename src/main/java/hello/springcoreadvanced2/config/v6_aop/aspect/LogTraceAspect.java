package hello.springcoreadvanced2.config.v6_aop.aspect;

import hello.springcoreadvanced2.trace.TraceStatus;
import hello.springcoreadvanced2.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

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
