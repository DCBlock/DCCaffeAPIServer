## CaffeAPIServer
Caffe API Server for DigiCAP

## 코딩 가이드
- IntelliJ에서 제공하는 기본 Code Style 사용

## Push Guide
- gitignore는 각자 생성하여 사용하고 서버에 push하지 않음
- 개발툴에서 사용하는 설정정보도 push하지 않음(ex:application.properties)
- **별도의 branch에서 작업해서 Push**
- **master에 merge는 한명이 진행**

## 개발 환경
- Spring Boot 2.1.0 Release
- Openjdk 1.8

## Library
- org.projectlombok:lombok
- mybatis: https://mvnrepository.com/artifact/org.mybatis/mybatis-spring
- Logback: https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
- commons io: https://mvnrepository.com/artifact/commons-io/commons-io
- WebClient: 
  - compile 'org.springframework.boot:spring-boot-starter-webflux'
  - compile 'org.projectreactor:reactor-spring:1.0.1.RELEASE'
- JWT: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
  - compile group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'.7'
    compile 'com.google.guava:guava:27.0.1-jre'
- Request, Response Logging: https://mvnrepository.com/artifact/org.zalando/logbook-spring-boot-starter
  - compile group: 'org.zalando', name: 'logbook-spring-boot-starter', version: '1.11.2'
  - compile group: 'org.zalando', name: 'logbook-core', version: '1.11.2'
