

## Add dependency

```
    spring-cloud-starter-netflix-eureka-client
```

## Add in yaml

```
eureka:
  client:
    serviceUrl:
      defaultZone: http://${EUREKA_HOST:localhost}:8761/eureka
    register-with-eureka: true
    fetch-registry: true

spring:
  application:
    name: room-service

```