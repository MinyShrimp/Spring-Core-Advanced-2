package hello.springcoreadvanced2.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {
    @Test
    void postProcessor() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        // beanA 이름으로 B 객체가 Bean으로 등록된다.
        B b = context.getBean("beanA", B.class);
        b.helloB();

        // A는 Bean으로 등록되지 않는다.
        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> context.getBean(A.class)
        );
    }

    @Slf4j
    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        /**
         * 빈 후처리기를 빈으로 등록
         */
        @Bean
        public AToBPostProcessor helloPostProcessor() {
            return new AToBPostProcessor();
        }
    }

    /**
     * 빈 후처리기 선언
     *
     * @see BeanPostProcessor
     */
    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {

        /**
         * 객체 생성 이후, @PostConstruct 호출 이후
         *
         * @param bean     새로 생성되는 Bean 객체
         * @param beanName 해당 Bean 객체의 이름
         * @return {@link A} -> {@link B}
         * @throws BeansException 런타임 에러
         */
        @Override
        public Object postProcessAfterInitialization(
                Object bean,
                String beanName
        ) throws BeansException {
            log.info("beanName = {}, bean = {}", beanName, bean);
            return bean instanceof A ? new B() : bean;
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }
}
