package hello.springcoreadvanced2.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    /**
     * 공통 로직 1과 공통 로직 2는 호출하는 메서드만 다르고 전체 코드 흐름이 완전히 같다.
     */
    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result1 = {}", result1);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result2 = {}", result2);
        // 공통 로직 2 종료
    }

    /**
     * 리플렉션을 사용하여 메서드를 동적으로 호출
     */
    @Test
    void reflection1() throws Exception {
        Class<?> classHello = Class.forName("hello.springcoreadvanced2.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1 = {}", result1);

        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2 = {}", result2);
    }

    /**
     * 위의 예제에서 invoke 부분을 공통 메서드로 빼버렸다.
     */
    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("hello.springcoreadvanced2.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
