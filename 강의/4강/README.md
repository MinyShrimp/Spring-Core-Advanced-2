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