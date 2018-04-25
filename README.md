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
* **JdbcTemplate**, **NamedParameterJdbcTemplate**은 자동으로 설정됨

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

## 30.4 Gemfire
* [Pivotal Gemfire](https://pivotal.io/kr/pivotal-gemfire) data management platform
* **spring-boot-starter-data-gemfire**
* no auto-configuration
* **@EnableGemfireRepositories** Spring Data Repositories 사용가능

## 30.5 Solr
* search engine
* [Spring Data Solr](https://github.com/spring-projects/spring-data-solr)
* **spring-boot-starter-data-solr**

### 30.5.1 Connecting to Solr
* **localhost:8983/solr**
```java
@Component
public class MyBean {

	private SolrClient solr;

	@Autowired
	public MyBean(SolrClient solr) {
		this.solr = solr;
	}

	// ...

}
```

### 30.5.2 Spring Data Solr Repositories
* **@SolrDocument** (JPA @Entity)

## 30.6 Elasticsearch
* **spring-boot-starter-data-elasticsearch**
* [Jest](https://github.com/searchbox-io/Jest) Spring Boot가 지원함

### 30.6.1 Connecting to Elasticsearch by Using Jest
* **localhost:9200** 
* **JestClient**
```properties
spring.elasticsearch.jest.uris=http://search.example.com:9200
spring.elasticsearch.jest.read-timeout=10000
spring.elasticsearch.jest.username=user
spring.elasticsearch.jest.password=secret
```
* **HttpClientConfigBuilderCustomizer**를 통해 추가 HTTP 설정을 할 수 있다.
```java
static class HttpSettingsCustomizer implements HttpClientConfigBuilderCustomizer {

	@Override
	public void customize(HttpClientConfig.Builder builder) {
		builder.maxTotalConnection(100).defaultMaxTotalConnectionPerRoute(5);
	}

}
```

### 30.6.2 Connecting to Elasticsearch by Using Spring Data
* Elasticsearch에 연결하려면 하나 이상의 클러스터 노드 주소를 입력해야함
* **ElasticsearchTemplate** or **TransportClient**
```properties
spring.data.elasticsearch.cluster-nodes=localhost:9300
```
```java
@Component
public class MyBean {

	private final ElasticsearchTemplate template;

	public MyBean(ElasticsearchTemplate template) {
		this.template = template;
	}

	// ...

}
```

### 30.6.3 Spring Data Elasticsearch Repositories
* **@Document** (JPA @Entity)

## 30.7 Cassandra
* **spring-boot-starter-data-cassandra**

30.7.1 Connecting to Cassandra
* **CassandraTemplate** or Cassandra **Session**
```properties
spring.data.cassandra.keyspace-name=mykeyspace
spring.data.cassandra.contact-points=cassandrahost1,cassandrahost2
```
```java
@Component
public class MyBean {

	private CassandraTemplate template;

	@Autowired
	public MyBean(CassandraTemplate template) {
		this.template = template;
	}

	// ...

}
```

### 30.7.2 Spring Data Cassandra Repositories
* **@Query**

## 30.8 Couchbase
* **spring-boot-starter-data-couchbase** and **spring-boot-starter-data-couchbase-reactive**

### 30.8.1 Connecting to Couchbase
```properties
spring.couchbase.bootstrap-hosts=my-host-1,192.168.1.123
spring.couchbase.bucket.name=my-bucket
spring.couchbase.bucket.password=secret
```
* 새로운 **Bucket**을 여는데 사용할 시간과 SSL support를 활성화 함
```properties
spring.couchbase.env.timeouts.connect=3000
spring.couchbase.env.ssl.key-store=/location/of/keystore.jks
spring.couchbase.env.ssl.key-store-password=secret
```

### 30.8.2 Spring Data Couchbase Repositories
* [reference documentation](https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/)
```java
@Component
public class MyBean {

	private final CouchbaseTemplate template;

	@Autowired
	public MyBean(CouchbaseTemplate template) {
		this.template = template;
	}

	// ...

}
```

## 30.9 LDAP
* in-memory LDAP server [UnboundID](https://www.ldap.com/unboundid-ldap-sdk-for-java) 지원
* **spring-boot-starter-data-ldap**

### 30.9.1 Connecting to an LDAP Server
```properties
spring.ldap.urls=ldap://myserver:1235
spring.ldap.username=admin
spring.ldap.password=secret
```

### 30.9.2 Spring Data LDAP Repositories
* **LdapTemplate**
```java
@Component
public class MyBean {

	private final LdapTemplate template;

	@Autowired
	public MyBean(LdapTemplate template) {
		this.template = template;
	}

	// ...

}
```

### 30.9.3 Embedded In-memory LDAP Server
* **com.unboundid:unboundid-ldapsdk**
```properties
spring.ldap.embedded.base-dn=dc=spring,dc=io
```

## 30.10 InfluxDB
* Time-series DB : 시계열 데이터를 저장하고 활용하는데에 특화된 database
* Time-series : 인플럭스DB가 주로 매트릭스나 이벤트를 처리하며, 이를 실시간 분석하는 역할을 한다는 의미
* Time-series 데이터 검색을 위해 최적화 된 시계열 데이터베이스

### 30.10.1 Connecting to InfluxDB
```properties
spring.influx.url=http://172.0.0.1:8086
```

# 31. Caching
* **@EnableCaching** 애노테이션이 활성화 되어있으면 자동으로 구성
```java
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component;

@Component
public class MathService {

	@Cacheable("piDecimals")
	public int computePiDecimal(int i) {
		// ...
	}

}
```
* **computePiDecimal**을 호출하기 전에 **piDecimals** 캐시에서 i 인수와 같은값을 찾음
* i 인수의 값을 찾으면 메소드 호출없이 리턴
* 값이 없으면 메소드가 호출되고 캐시가 업데이트 됨
* JSR-107 (JCache) 애노테이션인 **@CacheResult** 사용가능
* Spring Cache와 JCache annotations 혼합해서 사용하지 마라

## 31.1 Supported Cache Providers
* 실제 저장소를 제공하지 않음
* **org.springframework.cache.Cache**, **org.springframework.cache.CacheManager** 인터페이스
* **CacheManager**, **CacheResolver** 빈이 정의되어있지 않으면, Spring Boot는 다음순서로 provider를 찾는다
1. Generic
2. JCache (JSR-107) (EhCache 3, Hazelcast, Infinispan, and others)
3. EhCache 2.x
4. Hazelcast
5. Infinispan
6. Couchbase
7. Redis
8. Caffeine
9. Simple
* **spring-boot-starter-cache**

```java
@Bean
public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
	return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
		@Override
		public void customize(ConcurrentMapCacheManager cacheManager) {
			cacheManager.setAllowNullValues(false);
		}
	};
}
```

### 31.1.1 Generic
* **org.springframework.cache.Cache** bean이 하나라도 정의되어에 일반 캐싱이 사용됨

### 31.1.2 JCache (JSR-107)
* **JCacheCacheManager**는 **spring-boot-starter-cache**에 의해 제공
```properties
 # Only necessary if more than one provider is present
spring.cache.jcache.provider=com.acme.MyCachingProvider
spring.cache.jcache.config=classpath:acme.xml
```

* **javax.cache.cacheManager**를 정의하는 두가지 방법
1. **spring.cache.cache-names** 프로퍼티에 세팅. **javax.cache.configuration.Configuration** 빈이 정의되어있으면 커스터마이즈 할때 사용 됨
2. **org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer** 빈은 **CacheManager** 레퍼런스되 호출 됨


### 31.1.3 EhCache 2.x
* **ehcache.xml** 클래스패스에 있으면 사용됨
```properties
spring.cache.ehcache.config=classpath:config/another-config.xml
```

### 31.1.4 Hazelcast
[general support for Hazelcast](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-hazelcast)

### 31.1.5 Infinispan
* 기본설정파일 위치가 없으므로 명시적으로 지정해야함. 그렇지 않으면 디폴트 bootstrap이 사용

```properties
spring.cache.infinispan.config=infinispan.xml
```
* Spring Boot에서는 임베디드 모드로 제한

### 31.1.6 Couchbase
* main **Bucket**에 두개의 캐시(cache1, cache2), 2초뒤에 다른 **Bucket**에있는 cache3 실행
```properties
spring.cache.cache-names=cache1,cache2
```
```java
@Configuration
public class CouchbaseCacheConfiguration {

	private final Cluster cluster;

	public CouchbaseCacheConfiguration(Cluster cluster) {
		this.cluster = cluster;
	}

	@Bean
	public Bucket anotherBucket() {
		return this.cluster.openBucket("another", "secret");
	}

	@Bean
	public CacheManagerCustomizer<CouchbaseCacheManager> cacheManagerCustomizer() {
		return c -> {
			c.prepareCache("cache3", CacheBuilder.newInstance(anotherBucket())
					.withExpiration(2));
		};
	}

}
```

### 31.1.7 Redis
* configuration 생성할때 cache1 생성 후 10분뒤에 cache2 생성
```properties
spring.cache.cache-names=cache1,cache2
spring.cache.redis.time-to-live=600000
```

### 31.1.8 Caffeine
* 다음중 하나로 사용자 정의 할 수 있음
1. **spring.cache.caffeine.spec**에 정의 spec
2. **com.github.benmanes.caffeine.cache.CaffeineSpec** bean
3. **com.github.benmanes.caffeine.cache.Caffeine** bean

```properties
spring.cache.cache-names=cache1,cache2
spring.cache.caffeine.spec=maximumSize=500,expireAfterAccess=600s
```

### 31.1.9 Simple
* 다른 provider가 발견되지 않으면, cache store에 정의된 **ConcurrentHashMap** 설정
* 애플리케이션에 캐싱 라이브러리가 없으면 기본값이 됨
```properties
spring.cache.cache-names=cache1,cache2
```

### 31.1.10 None
* 특정 환경에서 캐싱을 사용하지 않을 경우
```properties
spring.cache.type=none
```

### 예제
* **@Cacheable** 속성 : 
1. value : 캐시 이름
2. key : 캐시 키
3. condition : 특정 조건을 넣어서 상황에 따라 캐시를 할지 하지 않을지 결정
4. keyGenerator, cacheManager...
```java
@Cacheable(value = "wonwoo", condition = "#name.length() < 10")
public String find(String name) {
  logger.info("cache find .. {}", name);
  return name;
}
```
* 캐시명은 wonwoo가 되고 key는 name, 파라미터(name)길이가 10보다 작을 때만 캐시를 함
```java
@Cacheable(key = "#name", value = "wonwoo", condition = "#name.length() < 10")
```

* **@CacheEvict** : 캐시 데이터를 삭제할 때
```java
@CacheEvict(key = "#name", value = "wonwoo")
public String update(String name) {
  logger.info("cache update .. {}", name);
  return name;
}
```
* 모든 캐시들을 지울려면
```java
@CacheEvict(key = "#name", value = "wonwoo", allEntries = true)
```

* **@CachePut** : 매번 메서드를 호출. 호출과 동시에 캐시에 보관하므로 보통 저장할 때 용이
```java
@CachePut(key = "#name", value = "wonwoo")
public String save(String name) {
  logger.info("cache save .. {}", name);
  return name;
}
```

# 32. Messaging

## 32.1 JMS
* **javax.jms.ConnectionFactory**

### 32.1.1 ActiveMQ Support
* **spring-boot-starter-activemq**
```properties
spring.activemq.broker-url=tcp://192.168.1.210:9876
spring.activemq.user=admin
spring.activemq.password=secret
```

* **org.apache.activemq:activemq-pool** 
* **PooledConnectionFactory**
```properties
spring.activemq.pool.enabled=true
spring.activemq.pool.max-connections=50
```

### 32.1.2 Artemis Support
* **spring-boot-starter-artemis**
* **org.apache.activemq:artemis-jms-server** 애플리케이션에 추가하면 임베디드 모드로 사용
```properties
spring.artemis.mode=native
spring.artemis.host=192.168.1.210
spring.artemis.port=9876
spring.artemis.user=admin
spring.artemis.password=secret
```

### 32.1.3 Using a JNDI ConnectionFactory
* 애플리케이션 서버에서 애플리케이션이 러닝 중이면 **JNDI**를 사용하는 **ConnectionFactory**를 찾음
* **java:/JmsXA**, **java:/XAConnectionFactory** 에서 확인
```properties
spring.jms.jndi-name=java:/MyConnectionFactory
```

### 32.1.4 Sending a Message
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	private final JmsTemplate jmsTemplate;

	@Autowired
	public MyBean(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	// ...

}
```

### 32.1.5 Receiving a Message
* **@JmsListener**
```java
@Component
public class MyBean {

	@JmsListener(destination = "someQueue")
	public void processMessage(String content) {
		// ...
	}

}
```

* **MessageConverter**
```java
@Configuration
static class JmsConfiguration {

	@Bean
	public DefaultJmsListenerContainerFactory myFactory(
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory =
				new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory());
		factory.setMessageConverter(myMessageConverter());
		return factory;
	}

}
```

* **@JmsListener**
```java
@Component
public class MyBean {

	@JmsListener(destination = "someQueue", containerFactory="myFactory")
	public void processMessage(String content) {
		// ...
	}

}
```

## 32.2 AMQP
* Advanced Message Queuing Protocol 
* **spring-boot-starter-amqp**

### 32.2.1 RabbitMQ support
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=secret
```

### 32.2.2 Sending a Message
```java
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	private final AmqpAdmin amqpAdmin;
	private final AmqpTemplate amqpTemplate;

	@Autowired
	public MyBean(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
		this.amqpAdmin = amqpAdmin;
		this.amqpTemplate = amqpTemplate;
	}

	// ...

}
```

### 32.2.3 Receiving a Message
```java
@Component
public class MyBean {

	@RabbitListener(queues = "someQueue")
	public void processMessage(String content) {
		// ...
	}

}
```

* **MessageConverter**
```java
@Configuration
static class RabbitConfiguration {

	@Bean
	public SimpleRabbitListenerContainerFactory myFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory =
				new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(myMessageConverter());
		return factory;
	}

}
```

* **@RabbitListener**
```java
@Component
public class MyBean {

	@RabbitListener(queues = "someQueue", containerFactory="myFactory")
	public void processMessage(String content) {
		// ...
	}

}
```

## 32.3 Apache Kafka Support
```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup
```

### 32.3.1 Sending a Message
```java
@Component
public class MyBean {

	private final KafkaTemplate kafkaTemplate;

	@Autowired
	public MyBean(KafkaTemplate kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	// ...

}
```

### 32.3.2 Receiving a Message
```java
@Component
public class MyBean {

	@KafkaListener(topics = "someTopic")
	public void processMessage(String content) {
		// ...
	}

}
```

### 32.3.3 Additional Kafka Properties
```properties
spring.kafka.properties.prop.one=first
spring.kafka.admin.properties.prop.two=second
spring.kafka.consumer.properties.prop.three=third
spring.kafka.producer.properties.prop.four=fourth
```

* **JsonDeserializer**
```properties
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.value.default.type=com.example.Invoice
spring.kafka.consumer.properties.spring.json.trusted.packages=com.example,org.acme
```

```properties
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.properties.spring.json.add.type.headers=false
```












