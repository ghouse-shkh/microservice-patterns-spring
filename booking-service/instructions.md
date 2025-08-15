# Booking Service – Fault Tolerance Setup

## 1. Add Dependency

Add the circuitbreaker-resilience4j starter to your `pom.xml` (ensure you have the Spring Cloud dependency management section added in your pom.xml):

```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>
```

## 2. Configure in `application.yml`

Configure Circuit breaker, retry and timelimiter settings :

```yaml
resilience4j:
  circuitbreaker:
    instances:
      roomService:
        registerHealthIndicator: true
        slidingWindowSize: 5
        minimumNumberOfCalls: 3
        permittedNumberOfCallsInHalfOpenState: 3
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        automaticTransitionFromOpenToHalfOpenEnabled: true
        slidingWindowType: COUNT_BASED
        ignoreExceptions:
          - feign.FeignException$NotFound
  timelimiter:
    instances:
      roomService:
        timeout-duration: 2s
  retry:
    instances:
      roomService:
        max-attempts: 3
        wait-duration: 500ms
```

## 2. Add fault tolerance to service call methods and provide fallback

```java
    @CircuitBreaker(name = "roomService", fallbackMethod = "fallbackAvailability")
    @Retry(name = "roomService")
    public AvailabilityResponse checkAvailability(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests) {
        ...
    }

    private AvailabilityResponse fallbackAvailability(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests,
            Throwable ex) {
        return response(roomId, checkIn, checkOut, false, "Room availability check failed. Please try later.");
    }

    @CircuitBreaker(name = "roomService", fallbackMethod = "fallbackSearch")
    @Retry(name = "roomService")
    @TimeLimiter(name = "roomService")
    public CompletableFuture<List<RoomDto>> searchRooms(String type, Long delayMs, Integer status) {
        ...
    }

    private CompletableFuture<List<RoomDto>> fallbackSearch(String type, Long delayMs, Integer status, Throwable ex) {
        return CompletableFuture.completedFuture(Collections.emptyList());
    }

    @GetMapping("/room-instance")
    @RateLimiter(name = "bookingService", fallbackMethod = "getInstanceInfoFb")
    public InstanceInfo getInstanceInfo() {
      ...
    }

```
