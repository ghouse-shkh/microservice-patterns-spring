# Booking Service 

## 1. Add Dependency
Add the Eureka client starter to your `pom.xml` (ensure you have the Spring Cloud dependency management section added in your pom.xml):
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

## 2. Update `application.yml`
Configure Eureka client settings and service name:
```yaml
spring:
  application:
    name: booking-service

eureka:
  client:
    serviceUrl:
      defaultZone: http://${EUREKA_HOST:localhost}:8761/eureka
    register-with-eureka: true
    fetch-registry: true
```
