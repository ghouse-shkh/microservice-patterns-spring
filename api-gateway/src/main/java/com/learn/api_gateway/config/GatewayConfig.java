package com.learn.api_gateway.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
public class GatewayConfig {

        @Value("${EUREKA_HOST:localhost}")
        private String eurekaHost;

        @Value("${FRONTEND_URLS:http://localhost:3000}")
        private String frontendUrls;

        @Bean
        public RouteLocator apiroutes(RouteLocatorBuilder builder) {
                String eurekaBaseUri = "http://" + eurekaHost + ":8761";

                return builder.routes()
                                // Booking Service
                                .route("booking-service", r -> r.path("/api/bookings/**")
                                                .uri("lb://booking-service"))

                                // Room Service
                                .route("room-service", r -> r.path("/api/rooms/**")
                                                .uri("lb://room-service"))

                                // Optional - To access Eureka via gateway
                                .route("eureka-server", r -> r.path("/services/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri(eurekaBaseUri))

                                // Optional - To access Eureka via gateway
                                .route("eureka-static", r -> r.path("/eureka/**", "/lastn/**")
                                                .uri(eurekaBaseUri))
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