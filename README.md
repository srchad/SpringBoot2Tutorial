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

> spring.datasource.url=jdbc:mysql://localhost/test
> spring.datasource.username=dbuser
> spring.datasource.password=dbpass
> spring.datasource.driver-class-name=com.mysql.jdbc.Driver
* 최소한 **spring.datasource.url**은 세팅해야함(Spring Boot tries to auto-configure an embedded database)
* **driver-class-name** 사용안해도 됨(Spring Boot가 찾아줌)
* [DataSourceProperties](https://github.com/spring-projects/spring-boot/blob/v2.0.1.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jdbc/DataSourceProperties.java) - **spring.datasource.hikari.***, **spring.datasource.tomcat.***, **spring.datasource.dbcp2.***

> spring.datasource.tomcat.max-wait=10000
> spring.datasource.tomcat.max-active=50 
> spring.datasource.tomcat.test-on-borrow=true

### 29.1.3 Connection to a JNDI DataSource
> spring.datasource.jndi-name=java:jboss/datasources/customers

## 29.2 Using JdbcTemplate
* **JdbcTemplate**, **NamedParameterJdbcTemplate**는 자동으로 설정됨 (**@Autowire**)

<code>
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
</code>

## 29.3 JPA and “Spring Data”
* **spring-boot-starter-data-jpa** : Hibernate, Spring Data JPA, Spring ORMs

### 29.3.1 Entity Classes
* Spring Boot는 **persistence.xml** 필요없이 “Entity Scanning”
* **@EnableAutoConfiguration** or **@SpringBootApplication** 서치
* **@Entity**, **@Embeddable**, or **@MappedSuperclass**

### 29.3.2 Spring Data JPA Repositories

