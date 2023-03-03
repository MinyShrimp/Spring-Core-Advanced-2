package hello.springcoreadvanced2.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 스프링이 제공하는 {@link MethodInterceptor}을 사용해서 프록시를 생성<br>
 * - 타겟이 없다 -> {@link MethodInvocation}에 들어있다.<br>
 * - {@link MethodInvocation#proceed()}로 프록시를 호출하여 결과값을 받아온다.
 */
@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(
            MethodInvocation invocation
    ) throws Throwable {

        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = [{}ms]", resultTime);

        return result;
    }
}
