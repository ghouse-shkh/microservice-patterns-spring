# API Gateway

## 1. Add Dependency
Add the circuitbreaker-resilience4j starter to your `pom.xml` (ensure you have the Spring Cloud dependency management section added in your pom.xml):

```xml
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-gateway-server-webflux</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
```

## 2. Add routing either in application.yml or java code
```yml
   cloud:
     gateway:
       server:
         webflux:
           routes:
             - id: room-service
               uri: lb://room-service
               predicates:
                 - Path=/api/rooms/**
             - .....
```

```java
    @Bean
    public RouteLocator apiroutes(RouteLocatorBuilder builder) {
            String eurekaBaseUri = "http://" + eurekaHost + ":8761";

            return builder.routes()
                            // Booking Service
                            .route("booking-service", r -> r.path("/api/bookings/**")
                                            .uri("lb://booking-service"))
                    ......
                    .build();
```     

## 3. Add CorsFilter bean
```java
    @Bean
    public CorsWebFilter corsFilter() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList(frontendUrls.split(",")));
            .....
            var source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return new CorsWebFilter(source);
    }
```  