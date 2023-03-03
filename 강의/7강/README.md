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

## 빈 후처리기 - 적용

## 빈 후처리기 - 정리

## 스프링이 제공하는 빈 후처리기 1

## 스프링이 제공하는 빈 후처리기 2

## 하나의 프록시, 여러 Advisor 적용