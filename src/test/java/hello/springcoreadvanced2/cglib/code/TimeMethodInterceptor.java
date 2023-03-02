package hello.springcoreadvanced2.cglib.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 목표 객체의 시간을 구하는 프록시 객체<br>
 * - CGLIB의 {@link MethodInterceptor}사용
 */
@Slf4j
@RequiredArgsConstructor
public class TimeMethodInterceptor implements MethodInterceptor {
    private final Object target;

    /**
     * @param obj    CGLIB로 생성된 프록시 객체
     * @param method Reflection Method
     * @param args   목표 객체의 메서드에 필요한 인수들
     * @param proxy  CBLIB가 제공하는 파라미터,
     *               invoke 메서드를 사용할 때 Method 말고 이것을 사용하라고 권장한다.
     */
    @Override
    public Object intercept(
            Object obj,
            Method method,
            Object[] args,
            MethodProxy proxy
    ) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = proxy.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = [{}ms]", resultTime);

        return result;
    }
}
