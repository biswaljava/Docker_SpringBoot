<!---
Current Directory : /biswaljava/git/docker-crash-course/05-microservices
-->

## Complete Code Example


### /netflix-zuul-api-gateway-server/Dockerfile

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8765
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```
---

### /netflix-zuul-api-gateway-server/pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.biswaljava.microservices</groupId>
	<artifactId>08-netflix-zuul-api-gateway-server</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>netflix-zuul-api-gateway-server</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Greenwich.RC2</spring-cloud.version>
	</properties>

	<dependencies>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

 		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<finalName>zuul-api-gateway</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<!-- Docker -->
			<plugin>
				<groupId>com.spotify</groupId>
				<artifactId>dockerfile-maven-plugin</artifactId>
				<version>1.4.10</version>
				<executions>
					<execution>
						<id>default</id>
						<goals>
							<goal>build</goal>
							<!-- <goal>push</goal> -->
						</goals>
					</execution>
				</executions>
				<configuration>
					<repository>in28min/${project.name}</repository>
					<tag>${project.version}</tag>
					<skipDockerInfo>true</skipDockerInfo>
				</configuration>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>


</project>
```
---

### /netflix-zuul-api-gateway-server/src/test/java/com/biswaljava/microservices/netflixzuulapigatewayserver/NetflixZuulApiGatewayServerApplicationTests.java

```java
package com.biswaljava.microservices.netflixzuulapigatewayserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetflixZuulApiGatewayServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
```
---

### /netflix-zuul-api-gateway-server/src/main/resources/application.properties

```properties
spring.application.name=netflix-zuul-api-gateway-server
server.port=8765

management.endpoints.web.base-path=/manage
management.endpoints.web.exposure.include=*

#logging.level.org.springframework=debug

# Eureka
#eureka.client.service-url.default-zone=http://localhost:8761/eureka
eureka.client.service-url.defaultZone=http://naming-server:8761/eureka/

spring.sleuth.sampler.probability=1.0

#Feign and Ribbon Timeouts
feign.client.config.default.connectTimeout=50000
feign.client.config.default.readTimeout=50000
ribbon.ConnectTimeout= 60000
ribbon.ReadTimeout= 60000

# Zipkin
#spring.zipkin.base-url=http://zipkin-server:9411/

# RabbitMQ
spring.rabbitmq.host=rabbitmq
```
---

### /netflix-zuul-api-gateway-server/src/main/java/com/biswaljava/microservices/netflixzuulapigatewayserver/ZuulLoggingFilter.java

```java
package com.biswaljava.microservices.netflixzuulapigatewayserver;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ZuulLoggingFilter extends ZuulFilter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		HttpServletRequest request = 
				RequestContext.getCurrentContext().getRequest();
		logger.info("request -> {} request uri -> {}", 
				request, request.getRequestURI());
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
}
```
---

### /netflix-zuul-api-gateway-server/src/main/java/com/biswaljava/microservices/netflixzuulapigatewayserver/NetflixZuulApiGatewayServerApplication.java

```java
package com.biswaljava.microservices.netflixzuulapigatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class NetflixZuulApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixZuulApiGatewayServerApplication.class, args);
	}
	
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
}
```
---

### /currency-exchange-service/Dockerfile

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8000
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```
---

### /currency-exchange-service/pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.biswaljava.microservices</groupId>
	<artifactId>05-currency-exchange-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>currency-exchange-service</name>

	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.1.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<maven-jar-plugin.version>3.1.1</maven-jar-plugin.version>
		<spring-cloud.version>Greenwich.RC2</spring-cloud.version>

	</properties>

	<dependencies>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>

 		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>


		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>javax.xml.bind</groupId>
			<artifactId>jaxb-api</artifactId>
		</dependency>
		<dependency>
			<groupId>com.sun.xml.bind</groupId>
			<artifactId>jaxb-impl</artifactId>
			<version>2.3.1</version>
		</dependency>
		<dependency>
			<groupId>org.glassfish.jaxb</groupId>
			<artifactId>jaxb-runtime</artifactId>
		</dependency>
		<dependency>
			<groupId>javax.activation</groupId>
			<artifactId>activation</artifactId>
			<version>1.1.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<finalName>currency-exchange-service</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<!-- Docker -->
			<plugin>
				<groupId>com.spotify</groupId>
				<artifactId>dockerfile-maven-plugin</artifactId>
				<version>1.4.10</version>
				<executions>
					<execution>
						<id>default</id>
						<goals>
							<goal>build</goal>
							<!-- <goal>push</goal> -->
						</goals>
					</execution>
				</executions>
				<configuration>
					<repository>in28min/${project.name}</repository>
					<tag>${project.version}</tag>
					<skipDockerInfo>true</skipDockerInfo>
				</configuration>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>
</project>
```
---

### /currency-exchange-service/src/test/java/com/biswaljava/microservices/currencyexchangeservice/CurrencyExchangeServiceApplicationTests.java

```java
package com.biswaljava.microservices.currencyexchangeservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyExchangeServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
```
---

### /currency-exchange-service/src/main/resources/data.sql

```
insert into exchange_value(id,currency_from,currency_to,conversion_multiple)
values(10001,'USD','INR',65);
insert into exchange_value(id,currency_from,currency_to,conversion_multiple)
values(10002,'EUR','INR',75);
insert into exchange_value(id,currency_from,currency_to,conversion_multiple)
values(10003,'AUD','INR',25);
```
---

### /currency-exchange-service/src/main/resources/application.properties

```properties
spring.application.name=currency-exchange-service
server.port=8000

spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true

#logging.level.org.springframework=debug
management.endpoints.web.base-path=/manage
management.endpoints.web.exposure.include=*

spring.security.user.name=biswaljava
spring.security.user.password=dummy

# Eureka
#eureka.client.service-url.default-zone=http://localhost:8761/eureka
eureka.client.service-url.defaultZone=http://naming-server:8761/eureka/

spring.sleuth.sampler.probability=1.0

#Feign and Ribbon Timeouts
feign.client.config.default.connectTimeout=50000
feign.client.config.default.readTimeout=50000
ribbon.ConnectTimeout= 60000
ribbon.ReadTimeout= 60000

# RabbitMQ
spring.rabbitmq.host=rabbitmq
```
---

### /currency-exchange-service/src/main/java/com/biswaljava/microservices/currencyexchangeservice/util/environment/EnvironmentConfigurationLogger.java

```java
package com.biswaljava.microservices.currencyexchangeservice.util.environment;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfigurationLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentConfigurationLogger.class);

	@SuppressWarnings("rawtypes")
	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		final Environment environment = event.getApplicationContext().getEnvironment();
		LOGGER.debug("====== Environment and configuration ======");
		LOGGER.debug("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
		final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
		StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
				.map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream).distinct()
				.forEach(prop -> {
					LOGGER.debug("{}", prop);
//					Object resolved = environment.getProperty(prop, Object.class);
//					if (resolved instanceof String) {
//						LOGGER.info("{}", environment.getProperty(prop));
//					}
				});
		LOGGER.debug("===========================================");
	}

}
```
---

### /currency-exchange-service/src/main/java/com/biswaljava/microservices/currencyexchangeservice/security/SecurityConfiguration.java

```java
package com.biswaljava.microservices.currencyexchangeservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests().anyRequest().permitAll()
        .and()
        .httpBasic().disable()
        .csrf().disable();
  }
}
```
---

### /currency-exchange-service/src/main/java/com/biswaljava/microservices/currencyexchangeservice/CurrencyExchangeServiceApplicationH2.java

```java
package com.biswaljava.microservices.currencyexchangeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CurrencyExchangeServiceApplicationH2 {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServiceApplicationH2.class, args);
	}
}
```
---

### /currency-exchange-service/src/main/java/com/biswaljava/microservices/currencyexchangeservice/resource/ExchangeValueRepository.java

```java
package com.biswaljava.microservices.currencyexchangeservice.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
	ExchangeValue findByFromAndTo(String from, String to);
}
```
---

### /currency-exchange-service/src/main/java/com/biswaljava/microservices/currencyexchangeservice/resource/CurrencyExchangeController.java

```java
package com.biswaljava.microservices.currencyexchangeservice.resource;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeController.class);

	@Autowired
	private ExchangeValueRepository repository;


	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to,
			@RequestHeader Map<String, String> headers) {

		printAllHeaders(headers);

		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);

		LOGGER.info("{} {} {}", from, to, exchangeValue);

		if (exchangeValue == null) {
			throw new RuntimeException("Unable to find data to convert " + from + " to " + to);
		}

		return exchangeValue;
	}

	private void printAllHeaders(Map<String, String> headers) {
		headers.forEach((key, value) -> {
			LOGGER.info(String.format("Header '%s' = %s", key, value));
		});
	}
}
```
---

### /currency-exchange-service/src/main/java/com/biswaljava/microservices/currencyexchangeservice/resource/ExchangeValue.java

```java
package com.biswaljava.microservices.currencyexchangeservice.resource;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ExchangeValue {

	@Id
	private Long id;

	@Column(name = "currency_from")
	private String from;

	@Column(name = "currency_to")
	private String to;

	private BigDecimal conversionMultiple;

	public ExchangeValue() {

	}

	public ExchangeValue(Long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}

	public Long getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

}
```
---

### /docker-compose-reference.yml

```
version: '3.7'
# Removed subprocess.CalledProcessError: Command '['/usr/local/bin/docker-credential-desktop', 'get']' returned non-zero exit status 1
# I had this:
# cat ~/.docker/config.json
# {"auths":{},"credsStore":"", "credsStore":"desktop","stackOrchestrator":"swarm"}
# I updated to this:
# {"auths":{},"credsStore":"","stackOrchestrator":"swarm"}
services:

  rabbitmq:
    container_name: rabbitmq
    image: rabbitmq:3.5.3-management
    ports:
      - "5672:5672"
      - "15672:15672"
    networks:
      - currency-microservices-network

  naming-server:
    build: netflix-eureka-naming-server
    container_name: naming-server
    ports:
      - "8761:8761"
      - "28787:8787"
    networks:
      - currency-microservices-network

  zipkin-server:
    image: openzipkin/zipkin
    container_name: zipkin
    environment:
      - STORAGE_TYPE=mem
      # Uncomment to enable debug logging
      # - JAVA_OPTS=-Dlogging.level.zipkin=DEBUG
    depends_on: # Start the depends_on first
      - rabbitmq
    environment:
      - RABBIT_URI=amqp://guest:guest@rabbitmq:5672
    ports:
      - 9411:9411
    networks:
      - currency-microservices-network

  currency-exchange-service:
    image: in28min/currency-exchange-service:0.0.1-SNAPSHOT
    container_name: currency-exchange-service
    #build:
      #context: ../05-simple-microservices/currency-exchange-service
      #context: .
      #dockerfile: Dockerfile
    ports:
      - "8000:8000"
    restart: always
    depends_on: # Start the depends_on first
      - rabbitmq
      - zipkin-server
      - naming-server
      - zuul-api-gateway
    environment:
      - RABBIT_URI=amqp://guest:guest@rabbitmq:5672
    networks:
      - currency-microservices-network

  currency-conversion-service:
    image: in28min/currency-conversion-service:0.0.1-SNAPSHOT
    container_name: currency-conversion-service
    #build:
      #context: ../05-simple-microservices/currency-conversion-service
      #context: .
      #dockerfile: Dockerfile
    ports:
      - "8100:8100"
    restart: always
    depends_on: # Start the depends_on first
      - rabbitmq
      - zipkin-server
      - currency-exchange-service
      - naming-server
      - zuul-api-gateway
    environment:
      - CURRENCY_EXCHANGE_URI=http://currency-exchange-service:8000
      - RABBIT_URI=amqp://guest:guest@rabbitmq:5672
    networks:
      - currency-microservices-network

  zuul-api-gateway:
    image: in28min/netflix-zuul-api-gateway-server:0.0.1-SNAPSHOT
    container_name: zuul-api-gateway
    #build:
      #context: ../05-simple-microservices/currency-conversion-service
      #context: .
      #dockerfile: Dockerfile
    ports:
      - "8765:8765"
    restart: always
    depends_on: # Start the depends_on first
      - rabbitmq
      - zipkin-server
      - naming-server
    environment:
      - RABBIT_URI=amqp://guest:guest@rabbitmq:5672
    networks:
      - currency-microservices-network
  
# Networks to be created to facilitate communication between containers
networks:
  currency-microservices-network:
```
---

### /docker-compose.yml

```
version: '3.7'
services:

  rabbitmq:
    image: rabbitmq:3.5.3-management
    ports:
      - "5672:5672"
      - "15672:15672"
    restart: always
    networks:
      - currency-compose-network

  naming-server:
    image: in28min/netflix-eureka-naming-server:0.0.1-SNAPSHOT
    ports:
      - "8761:8761"
    restart: always
    networks:
      - currency-compose-network

  zipkin-server:
    image: openzipkin/zipkin
    container_name: zipkin
    environment:
      STORAGE_TYPE: mem
      RABBIT_URI: amqp://guest:guest@rabbitmq:5672
    ports:
      - "9411:9411"
    restart: always
    depends_on:
      - rabbitmq
    networks:
      - currency-compose-network


  zuul-api-gateway:
    image: in28min/netflix-zuul-api-gateway-server:0.0.1-SNAPSHOT
    environment:
      RABBIT_URI: amqp://guest:guest@rabbitmq:5672
    ports:
      - "8765:8765"
    restart: always
    depends_on:
      - naming-server
      - rabbitmq
      - zipkin-server
    networks:
      - currency-compose-network

  currency-exchange-service:
    image: in28min/currency-exchange-service:0.0.1-SNAPSHOT
    environment:
      RABBIT_URI: amqp://guest:guest@rabbitmq:5672
    ports:
      - "8000:8000"
    restart: always
    depends_on:
      - naming-server
      - rabbitmq
      - zipkin-server
    networks:
      - currency-compose-network

  currency-conversion-service:
    image: in28min/currency-conversion-service:0.0.1-SNAPSHOT
    ports:
      - "8100:8100"
    restart: always
    environment:
      #CURRENCY_EXCHANGE_URI: http://currency-exchange-service:8000
      RABBIT_URI: amqp://guest:guest@rabbitmq:5672
    depends_on:
      - currency-exchange-service
      - naming-server
      - rabbitmq
      - zipkin-server
    networks:
      - currency-compose-network
  
# Networks to be created to facilitate communication between containers
networks:
  currency-compose-network:
```
---

### /netflix-eureka-naming-server/Dockerfile

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8761
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```
---

### /netflix-eureka-naming-server/pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.biswaljava.microservices</groupId>
	<artifactId>07-netflix-eureka-naming-server</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>netflix-eureka-naming-server</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Finchley.M8</spring-cloud.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>com.spotify</groupId>
				<artifactId>dockerfile-maven-plugin</artifactId>
				<version>1.4.10</version>
				<executions>
					<execution>
						<id>default</id>
						<goals>
							<goal>build</goal>
							<!-- <goal>push</goal> -->
						</goals>
					</execution>
				</executions>
				<configuration>
					<repository>in28min/${project.name}</repository>
					<tag>${project.version}</tag>
					<skipDockerInfo>true</skipDockerInfo>
				</configuration>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>


</project>
```
---

### /netflix-eureka-naming-server/src/test/java/com/biswaljava/microservices/netflixeurekanamingserver/NetflixEurekaNamingServerApplicationTests.java

```java
package com.biswaljava.microservices.netflixeurekanamingserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetflixEurekaNamingServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
```
---

### /netflix-eureka-naming-server/src/main/resources/application.properties

```properties
spring.application.name=netflix-eureka-naming-server
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```
---

### /netflix-eureka-naming-server/src/main/java/com/biswaljava/microservices/netflixeurekanamingserver/NetflixEurekaNamingServerApplication.java

```java
package com.biswaljava.microservices.netflixeurekanamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NetflixEurekaNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixEurekaNamingServerApplication.class, args);
	}
}
```
---

### /currency-conversion-service/Dockerfile

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8100
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```
---

### /currency-conversion-service/pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.biswaljava.microservices</groupId>
	<artifactId>06-currency-conversion-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>currency-conversion-service</name>

	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.7.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<maven-jar-plugin.version>3.1.1</maven-jar-plugin.version>
		<spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
	</properties>

	<dependencies>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>

 		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>javax.xml.bind</groupId>
			<artifactId>jaxb-api</artifactId>
		</dependency>
		<dependency>
			<groupId>com.sun.xml.bind</groupId>
			<artifactId>jaxb-impl</artifactId>
			<version>2.3.1</version>
		</dependency>
		<dependency>
			<groupId>org.glassfish.jaxb</groupId>
			<artifactId>jaxb-runtime</artifactId>
		</dependency>
		<dependency>
			<groupId>javax.activation</groupId>
			<artifactId>activation</artifactId>
			<version>1.1.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<finalName>currency-conversion-service</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<!-- Docker -->
			<plugin>
				<groupId>com.spotify</groupId>
				<artifactId>dockerfile-maven-plugin</artifactId>
				<version>1.4.10</version>
				<executions>
					<execution>
						<id>default</id>
						<goals>
							<goal>build</goal>
							<!-- <goal>push</goal> -->
						</goals>
					</execution>
				</executions>
				<configuration>
					<repository>in28min/${project.name}</repository>
					<tag>${project.version}</tag>
					<skipDockerInfo>true</skipDockerInfo>
				</configuration>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>
</project>
```
---

### /currency-conversion-service/src/test/java/com/biswaljava/microservices/currencyconversionservice/CurrencyConversionServiceApplicationTests.java

```java
package com.biswaljava.microservices.currencyconversionservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyConversionServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
```
---

### /currency-conversion-service/src/main/resources/application.properties

```properties
spring.application.name=currency-conversion-service
server.port=8100

management.endpoints.web.base-path=/manage
management.endpoints.web.exposure.include=*

#logging.level.org.springframework=debug
spring.security.user.name=biswaljava
spring.security.user.password=dummy

# Eureka
#eureka.client.service-url.default-zone=http://localhost:8761/eureka
eureka.client.service-url.defaultZone=http://naming-server:8761/eureka/

spring.sleuth.sampler.probability=1.0

#Feign and Ribbon Timeouts
feign.client.config.default.connectTimeout=50000
feign.client.config.default.readTimeout=50000
ribbon.ConnectTimeout= 60000
ribbon.ReadTimeout= 60000

# Zipkin
#spring.zipkin.base-url=http://zipkin-server:9411/

# RabbitMQ
spring.rabbitmq.host=rabbitmq
```
---

### /currency-conversion-service/src/main/java/com/biswaljava/microservices/currencyconversionservice/util/environment/EnvironmentConfigurationLogger.java

```java
package com.biswaljava.microservices.currencyconversionservice.util.environment;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfigurationLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentConfigurationLogger.class);

	@SuppressWarnings("rawtypes")
	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		final Environment environment = event.getApplicationContext().getEnvironment();
		LOGGER.debug("====== Environment and configuration ======");
		LOGGER.debug("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
		final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
		StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
				.map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream).distinct()
				.forEach(prop -> {
					LOGGER.debug("{}", prop);
//					Object resolved = environment.getProperty(prop, Object.class);
//					if (resolved instanceof String) {
//						LOGGER.info("{}", environment.getProperty(prop));
//					}
				});
		LOGGER.debug("===========================================");
	}

}
```
---

### /currency-conversion-service/src/main/java/com/biswaljava/microservices/currencyconversionservice/security/SecurityConfiguration.java

```java
package com.biswaljava.microservices.currencyconversionservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests().anyRequest().permitAll()
        .and()
        .httpBasic().disable()
        .csrf().disable();
  }
}
```
---

### /currency-conversion-service/src/main/java/com/biswaljava/microservices/currencyconversionservice/CurrencyConversionServiceApplication.java

```java
package com.biswaljava.microservices.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.biswaljava.microservices.currencyconversionservice.resource")
@EnableDiscoveryClient
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}
}
```
---

### /currency-conversion-service/src/main/java/com/biswaljava/microservices/currencyconversionservice/resource/CurrencyConversionController.java

```java
package com.biswaljava.microservices.currencyconversionservice.resource;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		LOGGER.info("Received Request to convert from {} {} to {}. ", quantity, from, to);

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

		BigDecimal convertedValue = quantity.multiply(response.getConversionMultiple());

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				convertedValue);
	}
}
```
---

### /currency-conversion-service/src/main/java/com/biswaljava/microservices/currencyconversionservice/resource/CurrencyExchangeServiceProxy.java

```java
package com.biswaljava.microservices.currencyconversionservice.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//http://localhost:8000
//@FeignClient(name = "currency-exchange-service", url = "${CURRENCY_EXCHANGE_URI:http://localhost:8000}")
//@FeignClient(name = "currency-exchange-service")
@FeignClient(name="netflix-zuul-api-gateway-server")
public interface CurrencyExchangeServiceProxy {

	// /currency-exchange/from/USD/to/INR
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,
			@PathVariable("to") String to);
}
```
---

### /currency-conversion-service/src/main/java/com/biswaljava/microservices/currencyconversionservice/resource/CurrencyConversionBean.java

```java
package com.biswaljava.microservices.currencyconversionservice.resource;

import java.math.BigDecimal;

public class CurrencyConversionBean {

	private Long id;

	private String from;

	private String to;

	private BigDecimal conversionMultiple;

	private BigDecimal quantity;

	private BigDecimal totalCalculatedAmount;


	public CurrencyConversionBean() {

	}

	public CurrencyConversionBean(Long id, String from, String to, BigDecimal conversionMultiple, BigDecimal quantity,
			BigDecimal totalCalculatedAmount) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.quantity = quantity;
		this.totalCalculatedAmount = totalCalculatedAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}

	public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}

}
```
---
