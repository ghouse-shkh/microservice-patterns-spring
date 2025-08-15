# API Gateway – Fault Tolerance Setup

## 1. Add Dependency
Add the circuitbreaker-resilience4j starter to your `pom.xml` (ensure you have the Spring Cloud dependency management section added in your pom.xml):

```xml
	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
		</dependency>
```

## 2. Ratelimiter and circuitbreaker

      Configure CircuitBreaker and Ratelimiter filters in routes
      Create beans for KeyResolver and RedisRateLimiter

## 3. Create Fallback Controller with appropriate messages

## 4 Configure in `application.yml`
Configure Circuit breaker, retry and timelimiter settings :
```yaml
resilience4j:
  circuitbreaker:
    instances:
      hotelBreaker:
        register-health-indicator: true
        slidingWindowSize: 5
        failureRateThreshold: 50
        waitDurationInOpenState: 5s