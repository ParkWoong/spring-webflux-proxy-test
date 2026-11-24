# WebFlux Proxy / WebFlux 프록시

[ENG](#eng) | [KR](#kr)

## ENG

### Project structure

```text
pom.xml
src/main/java/io/github/parkwoong/gateway/Application.java
src/main/java/io/github/parkwoong/gateway/backend/Backend.java
src/main/java/io/github/parkwoong/gateway/backend/BackendRepository.java
src/main/java/io/github/parkwoong/gateway/config/HashMapConverter.java
src/main/java/io/github/parkwoong/gateway/config/RouteData.java
src/main/java/io/github/parkwoong/gateway/config/WebClientConfig.java
src/main/java/io/github/parkwoong/gateway/config/WebFluxConfig.java
src/main/java/io/github/parkwoong/gateway/handler/IngressHandler.java
src/main/java/io/github/parkwoong/gateway/properties/IngressProperties.java
src/main/java/io/github/parkwoong/gateway/router/Router.java
src/main/java/io/github/parkwoong/gateway/util/StringSupporter.java
src/main/java/io/github/parkwoong/gateway/vo/Errors.java
src/main/resources/application.properties
src/test/java/io/github/parkwoong/gateway/ProxyTest.java
```

### Code explanation

Router sends requests to IngressHandler. The handler looks up a fixed route in H2 and forwards
the method, query, headers, and byte body with WebClient. WebClientConfig sets connection and
response timeouts. A sample /sample route points to localhost:9090.

### Run and test

Use JDK 8 and Maven 3.6.3+.

```sh
java -version
mvn clean verify
mvn spring-boot:run
```

Start a sample HTTP server on 127.0.0.1:9090, then call http://127.0.0.1:8080/sample. ProxyTest
checks that only the configured route exists.

#### Postman API

**Setup**

Serve a file named sample containing hello from localhost:9090.

##### Forward a configured route

**Request**

- Method: GET
- URL: http://localhost:8080/sample
- Body: none

**Response**

- Status: 200 OK

~~~text
hello
~~~

**Note:** An unconfigured path returns 400 Bad Request.

## KR

### 프로젝트 구조

```text
pom.xml
src/main/java/io/github/parkwoong/gateway/Application.java
src/main/java/io/github/parkwoong/gateway/backend/Backend.java
src/main/java/io/github/parkwoong/gateway/backend/BackendRepository.java
src/main/java/io/github/parkwoong/gateway/config/HashMapConverter.java
src/main/java/io/github/parkwoong/gateway/config/RouteData.java
src/main/java/io/github/parkwoong/gateway/config/WebClientConfig.java
src/main/java/io/github/parkwoong/gateway/config/WebFluxConfig.java
src/main/java/io/github/parkwoong/gateway/handler/IngressHandler.java
src/main/java/io/github/parkwoong/gateway/properties/IngressProperties.java
src/main/java/io/github/parkwoong/gateway/router/Router.java
src/main/java/io/github/parkwoong/gateway/util/StringSupporter.java
src/main/java/io/github/parkwoong/gateway/vo/Errors.java
src/main/resources/application.properties
src/test/java/io/github/parkwoong/gateway/ProxyTest.java
```

### 코드 설명

Router가 요청을 IngressHandler로 전달한다. 핸들러가 H2의 고정 경로를 조회하고 WebClient로
메서드·쿼리·헤더·바이트 본문을 전달한다. WebClientConfig가 연결·응답 타임아웃을 설정한다. /sample
경로는 localhost:9090을 가리킵니다.

### 실행과 테스트

JDK 8, Maven 3.6.3 이상 사용. 저장소 루트에서 실행.

```sh
java -version
mvn clean verify
mvn spring-boot:run
```

127.0.0.1:9090에서 샘플 HTTP 서버를 실행하고 http://127.0.0.1:8080/sample을 호출.
ProxyTest는 등록된 경로만 존재하는지 확인한다.

#### Postman API

**준비**

localhost:9090에서 내용이 hello인 sample 파일 제공.

##### 등록된 경로 전달

**요청**

- 메서드: GET
- URL: http://localhost:8080/sample
- 본문: 없음

**응답**

- 상태: 200 OK

~~~text
hello
~~~

**참고:** 등록되지 않은 경로는 400 Bad Request.
