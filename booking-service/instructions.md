# Booking Service 

## 1. Add Dependency

```xml
		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka</artifactId>
		</dependency>
```

## 2. Update `application.yml`
Configure Eureka client settings and service name:
```yaml
spring:
  application:
    name: booking-service


```
