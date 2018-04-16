# 29. Working with SQL Databases

## 29.1 Configure a DataSource
* <code>javax.sql.DataSource</code> DB에 연결하는 표준적인 방법을 제공
* 전통적으로 'DataSource' **URL**을 써서 DB에 연결
* [다양한 예제](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-configure-a-datasource)

### 29.1.1 Embedded Database Support
* in-memory 임베디드 DB를 사용하여 애플리케이션 개발하는게 유용함
* in-memory databases는 persistent storage를 제공 안함
* **H2**, **HSQL**, **Derby**의 auto-configure 제공
* 임베디드 DB가 자동 설정이 될려면 **spring-jdbc**가 있어야 함.(<code>spring-boot-starter-data-jpa</code>)

### 29.1.2 Connection to a Production Database
* pooling **DataSource**가 자동 설정됨
* Spring Boot는 다음의 알고리즘을 사용함
1. [HikariCP](https://github.com/brettwooldridge/HikariCP)(기본값)
2. Tomcat pooling DataSource
3. [Commons DBCP2](https://commons.apache.org/proper/commons-dbcp/)
* **spring-boot-starter-jdbc** 또는 **spring-boot-starter-data-jpa** 자동으로 HikariCP를 가져옴
* Tomcat container는 **tomcat-jdbc**를 제공함
* DataSource는 **spring.datasource.*** 시작하는 외부 configuration properties로 컨트롤 됨.

```properties
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```
* 최소한 **spring.datasource.url**은 세팅해야함(Spring Boot tries to auto-configure an embedded database)
* **driver-class-name** 사용안해도 됨(Spring Boot가 찾아줌)
* [DataSourceProperties](https://github.com/spring-projects/spring-boot/blob/v2.0.1.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jdbc/DataSourceProperties.java) - **spring.datasource.hikari.***, **spring.datasource.tomcat.***, **spring.datasource.dbcp2.***

```properties
spring.datasource.tomcat.max-wait=10000
spring.datasource.tomcat.max-active=50 
spring.datasource.tomcat.test-on-borrow=true
```

### 29.1.3 Connection to a JNDI DataSource
> spring.datasource.jndi-name=java:jboss/datasources/customers

## 29.2 Using JdbcTemplate
* **JdbcTemplate**, **NamedParameterJdbcTemplate**는 자동으로 설정됨 (**@Autowire**)

```java
import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Component;
    
    @Component
    public class MyBean {
    
        private final JdbcTemplate jdbcTemplate;
    
        @Autowired
        public MyBean(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    
        // ...
    
}
```


## 29.3 JPA and “Spring Data”
* **spring-boot-starter-data-jpa** : Hibernate, Spring Data JPA, Spring ORMs

### 29.3.1 Entity Classes
* Spring Boot는 **persistence.xml** 필요없이 “Entity Scanning”
* **@EnableAutoConfiguration** or **@SpringBootApplication** 서치
* **@Entity**, **@Embeddable**, or **@MappedSuperclass**

### 29.3.2 Spring Data JPA Repositories
*  **Repository** or **CrudRepository** 상속받음
```java
package com.example.myapp.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface CityRepository extends Repository<City, Long> {

	Page<City> findAll(Pageable pageable);

	City findByNameAndCountryAllIgnoringCase(String name, String country);

}
```

### 29.3.3 Creating and Dropping JPA Databases
* **spring.jpa.hibernate.ddl-auto=create-drop**
* **spring.jpa.properties.hibernate.hbm2ddl.auto=create-drop**
* **spring.jpa.properties.hibernate.globally_quoted_identifiers=true**

### 29.3.4 Open EntityManager in View
* **spring.jpa.open-in-view=false**

## 29.4 Using H2’s Web Console
* web application
* **com.h2database:h2** classpath에 있어야 함
* **spring-boot-devtools**

### 29.4.1 Changing the H2 Console’s Path
* **/h2-console** 기본 콘솔 경로
* **spring.h2.console.path**

## 29.5 Using jOOQ
* Java Object Oriented Querying ([jOOQ](http://www.jooq.org/))
* [Data Geekery](http://www.datageekery.com/)

### 29.5.1 Code Generation
```xml
<plugin>
	<groupId>org.jooq</groupId>
	<artifactId>jooq-codegen-maven</artifactId>
	<executions>
		...
	</executions>
	<dependencies>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>${h2.version}</version>
		</dependency>
	</dependencies>
	<configuration>
		<jdbc>
			<driver>org.h2.Driver</driver>
			<url>jdbc:h2:~/yourdatabase</url>
		</jdbc>
		<generator>
			...
		</generator>
	</configuration>
</plugin>
```

### 29.5.2 Using DSLContext
```java
@Component
public class JooqExample implements CommandLineRunner {

	private final DSLContext create;

	@Autowired
	public JooqExample(DSLContext dslContext) {
		this.create = dslContext;
	}

}
```
```java
public List<GregorianCalendar> authorsBornAfter1980() {
	return this.create.selectFrom(AUTHOR)
		.where(AUTHOR.DATE_OF_BIRTH.greaterThan(new GregorianCalendar(1980, 0, 1)))
		.fetch(AUTHOR.DATE_OF_BIRTH);
}
```

# 30. Working with NoSQL Technologies

## 30.1 Redis
* cache, message broker, and richly-featured key-value store
*  Spring Boot는 Lettuce, Jedis client libraries 제공
* **spring-boot-starter-data-redis**
* **spring-boot-starter-data-redis-reactive**

### 30.1.1 Connecting to Redis
* **RedisConnectionFactory**, **StringRedisTemplate**, **RedisTemplate** 스프링 빈으로 자동으로 설정
* Redis server at **localhost:6379**
```java
@Component
public class MyBean {

	private StringRedisTemplate template;

	@Autowired
	public MyBean(StringRedisTemplate template) {
		this.template = template;
	}

	// ...

}
```
* **commons-pool2** classpath에 있으면 pooled connection factory 사용함

## 30.2 MongoDB
* **spring-boot-starter-data-mongodb**
* **spring-boot-starter-data-mongodb-reactive**

### 30.2.1 Connecting to a MongoDB Database
* **org.springframework.data.mongodb.MongoDbFactory**
* 기본적으로 MongoDB server **mongodb://localhost/test** 접속함
```java
import org.springframework.data.mongodb.MongoDbFactory;
import com.mongodb.DB;

@Component
public class MyBean {

	private final MongoDbFactory mongo;

	@Autowired
	public MyBean(MongoDbFactory mongo) {
		this.mongo = mongo;
	}

	// ...

	public void example() {
		MongoDatabase db = mongo.getDb();
		// ...
	}
}
```
* Mongo 3.0 이상
```properties
spring.data.mongodb.uri=mongodb://user:secret@mongo1.example.com:12345,mongo2.example.com:23456/test
```

* Mongo 2.x 에서는 **host**/**port** 설정해야함
``` properties
spring.data.mongodb.host=mongoserver
spring.data.mongodb.port=27017
```

### 30.2.2 MongoTemplate
* Spring Data MongoDB는 [MongoTemplate](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html) 제공함(spring의 **JdbcTemplate**)
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public MyBean(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	// ...

}
```

### 30.2.3 Spring Data MongoDB Repositories

```java
package com.example.myapp.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface CityRepository extends Repository<City, Long> {

	Page<City> findAll(Pageable pageable);

	City findByNameAndCountryAllIgnoringCase(String name, String country);

}
```

### 30.2.4 Embedded Mongo
* **de.flapdoodle.embed:de.flapdoodle.embed.mongo**
* **spring.data.mongodb.port** (랜덤 port를 줄려면 값을 0으로)
* SLF4J가 클래스패스에 있으면 **org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongo**


## 30.3 Neo4j
* **spring-boot-starter-data-neo4j**

### 30.3.1 Connecting to a Neo4j Database
* **Neo4jSession**, **Session**, **Neo4jOperations**
* **localhost:7474**
```java
@Component
public class MyBean {

	private final Neo4jTemplate neo4jTemplate;

	@Autowired
	public MyBean(Neo4jTemplate neo4jTemplate) {
		this.neo4jTemplate = neo4jTemplate;
	}

	// ...

}
```
```properties
spring.data.neo4j.uri=http://my-server:7474
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=secret
```

### 30.3.2 Using the Embedded Mode
* **org.neo4j:neo4j-ogm-embedded-driver**
* **spring.data.neo4j.embedded.enabled=false** persistence룰 사용할 수 있다
```properties
spring.data.neo4j.uri=file://var/tmp/graph.db
```

### 30.3.3 Neo4jSession
* Open Session in View
```properties
spring.data.neo4j.open-in-view=false
```

### 30.3.4 Spring Data Neo4j Repositories
* JPA **@Entity** 대신에 Neo4j OGM **@NodeEntity**
* repository(@Transactional) 사용하려면 애노테이션 추가해야함
```java
@EnableNeo4jRepositories(basePackages = "com.example.myapp.repository")
@EnableTransactionManagement
```

### 30.3.5 Repository Example
```java
package com.example.myapp.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface CityRepository extends Neo4jRepository<City> {

	Page<City> findAll(Pageable pageable);

	City findByNameAndCountry(String name, String country);

}

```





