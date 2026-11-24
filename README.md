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

Router sends requests to IngressHandler. The handler looks up a fixed route in H2 and forwards the method, query, headers, and byte body with WebClient. WebClientConfig sets connection and response timeouts. A sample /sample route points to localhost:9090.

### Run and test

Use JDK 8 and Maven 3.6.3+.

```sh
java -version
mvn clean verify
mvn spring-boot:run
```

Start a sample HTTP server on 127.0.0.1:9090, then call http://127.0.0.1:8080/sample. ProxyTest checks that only the configured route exists.

#### Postman API

Start an HTTP server on localhost:9090 with a file named sample containing hello. Then call GET http://localhost:8080/sample with no body. Expected: HTTP 200 with body hello from the upstream server. Requests outside the configured route return HTTP 400.

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

Router가 요청을 IngressHandler로 전달합니다. 핸들러가 H2의 고정 경로를 조회하고 WebClient로 메서드·쿼리·헤더·바이트 본문을 전달합니다. WebClientConfig가 연결·응답 타임아웃을 설정합니다. /sample 경로는 localhost:9090을 가리킵니다.

### 실행과 테스트

JDK 8과 Maven 3.6.3 이상을 사용합니다.

```sh
java -version
mvn clean verify
mvn spring-boot:run
```

127.0.0.1:9090에서 샘플 HTTP 서버를 실행하고 http://127.0.0.1:8080/sample을 호출하세요. ProxyTest는 등록된 경로만 존재하는지 확인합니다.

#### Postman API

localhost:9090에서 hello가 담긴 sample 파일을 제공하는 HTTP 서버를 실행하세요. 본문 없이 GET http://localhost:8080/sample을 호출하면 업스트림에서 온 HTTP 200과 본문 hello가 예상됩니다. 등록되지 않은 경로는 HTTP 400을 반환합니다.
