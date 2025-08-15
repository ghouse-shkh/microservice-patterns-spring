# Eureka Server Setup

## 1. Add Dependency
Add the following dependency to your `pom.xml` (ensure Spring Cloud dependency management is configured):
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

## 2. Add Annotation
Annotate your main application class to enable the Eureka server:
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

## 3. Update Configuration
Set the server port and disable client behavior for the server:
```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```