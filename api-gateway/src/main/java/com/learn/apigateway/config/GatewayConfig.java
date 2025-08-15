package com.learn.apigateway.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

        @Value("${FRONTEND_URLS:http://localhost:3000}")
        private String frontendUrls;

        @Bean
        public RedisRateLimiter redisRateLimiter() {
                return new RedisRateLimiter(2, 4);
        }

        @Bean
        public KeyResolver ipKeyResolver() {
                return exchange -> Mono.just(
                                exchange
                                                .getRequest()
                                                .getRemoteAddress()
                                                .getAddress()
                                                .getHostAddress());
        }

        @Bean
        public RouteLocator apiroutes(RouteLocatorBuilder builder) {
                return builder.routes()
                                // Booking Service
                                .route("booking-service", r -> r.path("/api/bookings/**")
                                                .filters(f -> f
                                                                .requestRateLimiter(config -> {
                                                                        config.setKeyResolver(ipKeyResolver());
                                                                        config.setRateLimiter(redisRateLimiter());
                                                                })
                                                                .circuitBreaker(config -> config
                                                                                .setName("hotelBreaker")
                                                                                .setFallbackUri("forward:/fallback/bookings")))
                                                .uri("lb://booking-service"))

                                // Room Service
                                .route("room-service", r -> r.path("/api/rooms/**")
                                                .uri("lb://room-service"))
                                .build();
        }

        @Bean
        public CorsWebFilter corsFilter() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList(frontendUrls.split(",")));
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Arrays.asList("*"));

                var source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);

                return new CorsWebFilter(source);
        }
}