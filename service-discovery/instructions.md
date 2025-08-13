Add dependency 
		
        <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>


Add Annotation:

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {...}



Add in configuration

server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false