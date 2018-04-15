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



