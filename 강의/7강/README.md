# 빈 후처리기

## 빈 후처리기 - 소개

### 일반적인 스프링 빈 등록

![img.png](img.png)

`@Bean`이나 컴포넌트 스캔으로 스프링 빈을 등록하면,
스프링은 대상 객체를 생성하고 스프링 컨테이너 내부의 빈 저장소에 등록한다.

그리고 이후에는 스프링 컨테이너를 통해 등록한 스프링 빈을 조회해서 사용하면 된다.

### 빈 후처리기

#### BeanPostProcessor

스프링이 빈 저장소에 등록할 목적으로 생성한 객체를
빈 저장소에 등록하기 직전에 조작하고 싶다면 빈 후처리기를 사용하면 된다.

빈 포스트 프로세서(`BeanPostProcessor`)는 번역하면 빈 후처리기인데,
이름 그대로 **빈을 생성한 후에 무언가를 처리하는 용도**로 사용한다.

#### 기능

* 객체 조작
* 다른 객체로 바꿔치기

#### 과정

![img_1.png](img_1.png)

1. **생성**
    * 스프링 빈 대상이 되는 객체를 생성한다. (`@Bean`, 컴포넌트 스캔 **모두 포함**)
2. **전달**
    * 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.
3. **후 처리 작업**
    * 빈 후처리기는 전달된 스프링 빈 객체를 조작하거나 다른 객체로 바뀌치기 할 수 있다.
4. **등록**
    * 빈 후처리기는 빈을 반환한다.
    * 전달 된 빈을 그대로 반환하면 해당 빈이 등록되고, 바꿔치기 하면 다른 객체가 빈 저장소에 등록된다.

#### 다른 객체로 바꿔치기

![img_2.png](img_2.png)

## 빈 후처리기 - 예제 코드 1

### 일반적인 빈 등록

![img.png](img.png)

#### BasicTest

```java
public class BasicTest {
    @Test
    void basicConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        // A는 Bean으로 등록된다.
        A a = context.getBean("beanA", A.class);
        a.helloA();

        // B는 Bean으로 등록되지 않는다.
        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> context.getBean(B.class)
        );
    }

    @Slf4j
    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
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
```

## 빈 후처리기 - 예제 코드 2

### 빈 후처리기 적용

![img_3.png](img_3.png)

#### BeanPostProcessor

```java
package org.springframework.beans.factory.config;

public interface BeanPostProcessor {

   /**
    * 객체 생성 이후, @PostConstruct 호출 전
    */
   @Nullable
   default Object postProcessBeforeInitialization(
            Object bean, 
            String beanName
   ) throws BeansException {
      return bean;
   }
   
   /**
    * 객체 생성 이후, @PostConstruct 호출 후
    */
   @Nullable
   default Object postProcessAfterInitialization(
            Object bean, 
            String beanName
   ) throws BeansException {
      return bean;
   }
}
```

### 테스트

#### BeanPostProcessorTest

```java
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
}
```

#### 결과 로그

```
# 스프링 빈 컨텍스트 시작
[     AnnotationConfigApplicationContext] - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@2145b572

# 자동 빈 생성
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerProcessor'
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalCommonAnnotationProcessor'

# @Configuration의 빈 생성
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'helloPostProcessor'
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'beanPostProcessorTest.BasicConfig'

# BeanPostProcessor를 등록함에 따라 자동 프록시 등록이 해제 되었다.
[               BeanPostProcessorChecker] - Bean 'beanPostProcessorTest.BasicConfig' of type [hello.springcoreadvanced2.postprocessor.BeanPostProcessorTest$BasicConfig$$SpringCGLIB$$0] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)

# A 객체의 빈 생성
[             DefaultListableBeanFactory] - Creating shared instance of singleton bean 'beanA'

# BeanPostProcessor#postProcessAfterInitialization 의 처리
# A 객체의 빈은 제거되고, B 객체의 빈으로 교체
[BeanPostProcessorTest$AToBPostProcessor] - beanName = beanA, bean = hello.springcoreadvanced2.postprocessor.BeanPostProcessorTest$A@1205bd62

# 테스트 코드 실행
[                BeanPostProcessorTest$B] - hello B
```

### 정리

빈 후처리기는 빈을 조작하고 변경할 수 있는 **후킹 포인트**이다.
이것은 빈 객체를 조작하거나 심지어 다른 객체로 바꾸어 버릴 수 있을 정도로 막강하다.
여기서 조작이라는 것은 해당 객체의 특정 메서드를 호출하는 것을 뜻한다.

일반적으로 스프링 컨테이너가 등록하는, 특히 컴포넌트 스캔의 대상이 되는 빈들은 중간에 조작할 방법이 없는데,
빈 후처리기를 사용하면 개발자가 등록하는 모든 빈을 중간에 조작할 수 있다.

이 말은 빈 객체를 **프록시로 교체**하는 것도 가능하다는 뜻이다.

#### @PostConstruct 의 비밀

`@PostConstruct`는 스프링 빈 생성 이후에 빈을 초기화 하는 역할을 한다.
그런데 생각해보면 빈의 초기화 라는 것이 단순히 `@PostConstruct` 애노테이션이 붙은 초기화 메서드를 한번 호출만 하면 된다.
쉽게 이야기해서 생성된 빈을 한번 조작하는 것이다.

따라서 빈을 조작하는 행위를 하는 적절한 빈 후처리기가 있으면 될 것 같다.

스프링은 `CommonAnnotationBeanPostProcessor`라는 빈 후처리기를 자동으로 등록하는데,
여기에서 `@PostConstruct`애노테이션이 붙은 메서드를 호출한다.

따라서 스프링 스스로도 스프링 내부의 기능을 확장하기 위해 빈 후처리기를 사용한다.

## 빈 후처리기 - 적용

## 빈 후처리기 - 정리

## 스프링이 제공하는 빈 후처리기 1

## 스프링이 제공하는 빈 후처리기 2

## 하나의 프록시, 여러 Advisor 적용