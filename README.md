# CaffeAPIServer
DigiCAP Caffe API Server

## 코딩 가이드
- ~Sun 코딩 가이드를 준수~
- ~tab은 사용하지 않고 4 space를 사용~
- ~한 줄의 최대 길이 제한 없습니다(개발툴에서 Word Wrap 사용)~
- [google java style guide](https://google.github.io/styleguide/javaguide.html)를 준수

## Push Guide
- ~개발자들은 master에 직접 개발, push 하지 않습니다.~
- ~별도의 브랜치를 생성하여 담당 영역을 개발합니다.~
- ~예를 들면, 저는 'shseo' 브랜치를 생성하고 여기에서 개발하고 push합니다.~
- ~master에 merge는 제가 진행하겠습니다.~
- ~수홍 과장은 master 새로운 push 올라오면 자신의 브런치에 merge를 하면 됩니다.~
- gitignore는 각자 생성하여 사용하고 서버에 push하지 않음
- 개발툴에서 사용하는 각종 설정정보도 push하지 않음
- 개발 담당 변경으로 master branch에 push

## 개발 환경
- Spring Boot 2.1.0 Release
- Openjdk 1.8

## Proxy 환경에서 X-Forward-For 설정 하기
Client -> L4(Proxy) -> Application과 같은 프록시 환경에서는 Application에 접속하는 Remote_host가 L4(Proxy) IP로 표시된다.
이 경우에 Remote_host의 IP를 확인해야 할 경우 Header의 X-Forward-For를 확인하면 된다.

> SpringBoot에서 X-Forward-For 설정하기 [[참고 링크]](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html)
> application.properties는 다음 예제와 같이 항목을 추가
```
server.tomcat.remote-ip-header = x-forwarded-for
server.tomcat.protocol-header = x-forwarded-proto
```

## Eclipse / STS 환경 설정
- lombok 라이브러리를 사용하기 때문에 별도의 개발툴 환경 설정이 필요
- Eclipse / STS: [바로가기](http://countryxide.tistory.com/16)
- IDEA: [바로가기](http://blog.woniper.net/229)

# License
GNU GENERAL PUBLIC LICENSE 3.0

## for Mybatis
- [mybatis](https://mvnrepository.com/artifact/org.mybatis/mybatis)
- [mybatis-spring](https://mvnrepository.com/artifact/org.mybatis/mybatis-spring)
- [mybatis-spring-boot-starter](https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter)


## for JWT
- [jsonwebtoken](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)

## util 
- [guava]()
- [commons-io](https://mvnrepository.com/artifact/commons-io/commons-io)

## log
- [logback](https://mvnrepository.com/artifact/ch.qos.logback/logback-classic)
- [logback-core](https://mvnrepository.com/artifact/ch.qos.logback/logback-core)
- [logbook](https://mvnrepository.com/artifact/org.zalando/logbook-core)
