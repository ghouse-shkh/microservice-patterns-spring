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

--- 

## 4. Optional feature/settings

### Access info of services in json format

  http://localhost:8761/eureka/apps
---
### For Clients
- **`eureka.instance.hostname`** *(string)*  
  Used for service identification. Defaults to the machine hostname if not set.

- **`eureka.instance.prefer-ip-address`** *(boolean)*  
  If `true`, the service will register using its IP address instead of its hostname.

### For Server
- **`eureka.server.enable-self-preservation`** *(boolean, default `true`)*  
  Prevents Eureka from expiring instances when it stops receiving the expected number of heartbeats (helps avoid mass eviction during network issues).  
  If `false`, Eureka will evict instances immediately when their lease expires, which can be risky in unstable networks.

- **`eureka.server.self-preservation-percentage`** *(decimal, default `0.85`)*  
  The threshold of renewals (as a fraction of the expected number) below which the self-preservation mode is triggered.