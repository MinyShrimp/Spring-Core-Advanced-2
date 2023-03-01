# 프록시 패턴과 데코레이터 패턴

## 프로젝트 생성

### Spring initializer

* https://start.spring.io/
* 프로젝트 선택
    * Project: Gradle - Groovy
    * Language: Java 17
    * Spring Boot: 3.0.3
* Project Metadata
    * Group: hello
    * Artifact: spring-core-advanced-2
    * Packaging: Jar
* Dependencies
    * Spring Web, Lombok

### build.gradle

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    // 테스트에서 lombok 사용
    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
}
```

## 예제 프로젝트 만들기 V1

### 예제는 크게 3가지 상황으로 만든다.

* v1 - 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록
* v2 - 인터페이스 없는 구체 클래스 - 스프링 빈으로 수동 등록
* v3 - 컴포넌트 스캔으로 스프링 빈 자동 등록

### V1 - 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록

#### OrderRepository V1

```java
/**
 * OrderRepository 인터페이스<br>
 * - {@link OrderRepositoryV1Impl}
 */
public interface OrderRepositoryV1 {

    /**
     * 저장 로직
     *
     * @param itemId 아이템 ID
     */
    void save(String itemId);
}
```

#### OrderRepository V1 Impl

```java
/**
 * {@link OrderRepositoryV1} 구현체
 */
@Slf4j
public class OrderRepositoryV1Impl implements OrderRepositoryV1 {

    /**
     * 상품 저장 로직
     *
     * @param itemId 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @Override
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    /**
     * {@link Thread#sleep}, {@link InterruptedException} Wrapper
     *
     * @param millis 중지할 시간
     */
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.info("", e);
        }
    }
}
```

#### OrderService V1

```java
/**
 * OrderService 인터페이스<br>
 * - {@link OrderServiceV1Impl}
 */
public interface OrderServiceV1 {

    /**
     * 상품 주문 비즈니스 로직
     *
     * @param itemId 상품 ID
     */
    void orderItem(String itemId);
}
```

#### OrderService V1 Impl

```java
/**
 * {@link OrderServiceV1} 구현체
 */
@RequiredArgsConstructor
public class OrderServiceV1Impl implements OrderServiceV1 {
    private final OrderRepositoryV1 orderRepository;

    /**
     * 상품 저장 비즈니스 로직
     *
     * @param itemId 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
```

#### OrderController V1

```java
/**
 * OrderController 인터페이스<br>
 * - {@link OrderControllerV1Impl}<br>
 * - {@link Controller}, {@link RestController}가 없으면 인식 못함
 */
@RestController
@RequestMapping("/v1")
public interface OrderControllerV1 {

    /**
     * GET /v1/request
     *
     * @param itemId 상품 ID
     * @return ECHO 상품 ID
     */
    @GetMapping("/request")
    String request(@RequestParam("itemId") String itemId);

    /**
     * GET /v1/no-log
     *
     * @return "ok"
     */
    @GetMapping("/no-log")
    String noLog();
}
```

* 스프링 부트 3.0 미만
    * `@Controller`, `@RequestMapping`이 있어야 컨트롤러로 인식한다.
* 스프링 부트 3.0 이상
    * `@Controller`, `@RestController`가 있어야 컨트롤러로 인식한다.
    * `@RequestMapping`은 더이상 컨트롤러로 인식하지 않는다.
* `@Controller`, `@RestController`의 경우 `@Component`를 상속받고 있기 때문에, 별도의 설정을 하지않으면 자동으로 스프링 빈에 등록된다.

#### OrderController V1 Impl

```java
/**
 * {@link OrderControllerV1} 구현체<br>
 * - 의존: {@link OrderServiceV1}
 */
@Slf4j
@RequiredArgsConstructor
public class OrderControllerV1Impl implements OrderControllerV1 {
    private final OrderServiceV1 orderService;

    /**
     * GET /v1/request
     *
     * @param itemId 상품 ID
     * @return ECHO 상품 ID
     * @throws IllegalStateException itemId.equals("ex")
     */
    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return itemId;
    }

    /**
     * GET /v1/no-log
     *
     * @return "noLog ok"
     */
    @Override
    public String noLog() {
        return "noLog ok";
    }
}
```

#### AppV1Config

```java
/**
 * 수동 빈 등록
 */
@Configuration
public class AppV1Config {

    /**
     * @return {@link OrderControllerV1Impl}
     */
    @Bean
    public OrderControllerV1 orderController() {
        return new OrderControllerV1Impl(orderService());
    }

    /**
     * @return {@link OrderServiceV1Impl}
     */
    @Bean
    public OrderServiceV1 orderService() {
        return new OrderServiceV1Impl(orderRepository());
    }

    /**
     * @return {@link OrderRepositoryV1Impl}
     */
    @Bean
    public OrderRepositoryV1 orderRepository() {
        return new OrderRepositoryV1Impl();
    }
}
```

#### MainApplication

```java
@Import(AppV1Config.class)
@SpringBootApplication(scanBasePackages = "hello.springcoreadvanced2.app.v3")
public class ProxyApplication { ... }
```

* `@Import`
    * 해당 클래스를 스프링 빈에 등록한다.
    * 일반적으로 `@Configuration`같은 설정 파일을 등록할 때 사용한다.
    * 스프링 빈을 등록할 수도 있다.
* `scanBasePackages`
    * 컴포넌트 스캔 시작 위치 설정

## 예제 프로젝트 만들기 V2

## 예제 프로젝트 만들기 V3

## 요구사항 추가

## 프록시, 프록시 패턴, 데코레이터 패턴 - 소개

## 프록시 패턴 - 예제 코드 1

## 프록시 패턴 - 예제 코드 2

## 데코레이터 패턴 - 예제 코드 1

## 데코레이터 패턴 - 예제 코드 2

## 데코레이터 패턴 - 예제 코드 3

## 프록시 패턴과 데코레이터 패턴 정리

## 인터페이스 기반 프록시 - 적용

## 구체 클래스 기반 프록시 - 예제 1

## 구체 클래스 기반 프록시 - 예제 2

## 구체 클래스 기반 프록시 - 적용

## 인터페이스 기반 프록시와 클래스 기반 프록시