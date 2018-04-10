# SpringBoot2Tutorial

## 27.1 The “Spring Web MVC Framework” *

### 27.1.1 Spring MVC Auto-configuration *
 - 대부분의 application에서 잘 working 하는 Spring MVC의 auto-configuration을 제공한다.
     - ContentNegotiatingViewResolver : 요청이 원하는 뷰를 내부로직으로 판단해서 보내준다.
     - BeanNameViewResolver : "main"이라는 스트링이 리턴되면 View로 바꿔줘야하는데 이걸 BeanNameViewResolver가 해준다.
        - "main" -> BeanNameViewResolver(여러개가 탐색될 수 있다) -> View -> ContentNegotiatingViewResolver(판단) -> View
 - ContentNegotiatingViewResolver, BeanNameViewResolver 빈의 포함, 정작 자원 서비스 지원, WebJars에 대한 지원 등등
 - Spring MVC를 완벽하게 제어하고 싶다면, 자신만의 @Configuration을 사용 할 수 있다. 이때 @EnableWebMvc과 같이 사용해선 안된다.

### 27.1.2 HttpMessageConverters *
 - Spring MVC는 HttpMessageConverter인터페이스를 사용하여 HTTP 요청과 응답을 변환한다. 기본적으로 객체는 JSON 또는 XML, 문자열 인코딩은 UTF-8로 제공한다.
 - 이때 converter를 추가하거나 재정의해야하는 경우 스프링 부트의 HttpMessageConverters 클래스를 사용할 수 있다.

### 27.1.3 Custom JSON Serializers and Deserializers
 - Jackson을 사용하여 JSON 데이터를 Serialize, Deserialize 하는 경우 자체 JsonSerializer 및 JsonDeserializer 클래스를 작성 할 수 있다.
 - Custom serializer는 대게 모듈을 통해 Jackson에 등록되지만 Spring Boot는 @JsonComponent 어노테이션을 사용해 Spring Bean을 등록하기 쉽게 도와준다.

### 27.1.4 MessageCodesResolver *
 - 스프링 MVC는 바인딩 에러에서 오류메세지를 렌더링 하기위한 오류코드 생성 전략을 갖고있는데, spring.mvc.message-codes-resolver.format 프로퍼티에서 PREFIX_ERROR_CODE나 POSTFIX_ERROR_CODE를 세팅해주면 스프링부트에서 이를 만들어 줄것이다.
 - prefix_error_code : 에러코드가 앞에 나오고 오브젝트 name + 필드 순
 - postfix_error_code : 오브젝트 name + 필드 + 에러코드 순
 - 아마 다신 안쓸 내용. (실습 validation 참고)

### 27.1.5 Static Content *
 - 기본적으로 스프링 부트는 ServletContext의 root 클래스패스 안의 /static, /public, /resources 디렉토리에 있는 정적 content를 서빙해준다.
 - 컨테이너가 제공하는 디폴트 서블릿에게 Fallback 한다는 설정이 spring boot에는 기본적으로 되어있다. (index.html이 컨트롤러 없이 뜨는 이유)
 - 기본적으로는 class-path root 에있는 모든 리소스를 제공하는데 이를 커스터마이징 하고 싶으면  spring.mvc.static-path-pattern=/resources/** 이런식으로 설정해줄 수 있다.
 - front-end 리소스를 webjar로 떙겨와서 maven에 디펜던시를 걸어주고 "/webjars/jquery/x.y.z/jquery.min.js"과 같은 식으로 불러와 사용할 수 있다.
 - webjars-locator-core dependency를 추가하면 "/webjars/jquery/x.y.z/jquery.min.js"에서 x.y.z 같은 버전을 생략 할 수 있다.
 - jar로 패키징할때는 src/main/webapp 이 경로를 사용하지 않아야한다. 이 경로는 war 패키징 시에만 사용된다.
 - cache-busting static resources 지원 

### 27.1.6 Welcome Page
 - index.html을 먼저 찾고 만약 없으면 index 템플릿을 찾는다. 그래서 찾아지는게 있으면 그걸 welcome page로 사용한다.
 
### 27.1.7 Custom Favicon
 - favicon.ico를 static content locations나 root 클래스패스에서 찾아서 있으면 그걸 favicon으로 사용한다.
 
### 27.1.8 Path Matching and Content Negotiation
 - 스프링 MVC에서는 GET /projects/spring-boot.json 이 요청이 @GetMapping("/projects/spring-boot") 여기로 맵핑되었지만 부트에선 그렇지 않다.
 - Accept request 헤더에 명시하거나 쿼리 파라미터("GET /projects/spring-boot?format=json")를 사용해야 @GetMapping("/projects/spring-boot")로 맵핑될 수 있다.
 - 쿼리 파라미터를 사용하고 싶다면(?format=json) spring.mvc.contentnegotiation.favor-parameter=true 설정을 해준다.
 - 허나 만약 spring-boot.json / spring-boot.xml 등의 요청을 사용하고 싶다면 
    - spring.mvc.contentnegotiation.favor-path-extension=true
    - spring.mvc.pathmatch.use-registered-suffix-pattern=true 설정을 준다.
   
### 27.1.10 Template Engines
 - FreeMarker, Groovy, Thymeleaf, Mustache 이와 같은 템플릿 중 하나를 사용 한다면 "src/main/resources/templates" 경로 밑에서 자동으로 템플릿을 찾아준다.
 - jsp는 권장하지 않는데 embedded servlet container를 사용할 때 몇가지 제약사항이 있기때문. (27.4.5 JSP Limitations 참고)

### 27.1.11 Error Handling *
 - @ControllerAdvice 어노테이션을 사용하여 특정한 controller나 exception 타입의 return JSON document를 커스터마이징 할 수 있다.
 - Custom Error Page를 만들고 싶으면 /error folder아래 static HTML이나 템플릿을 넣는데 status code나 series mask를 파일명으로 쓰면 된다.
 - @ExceptionHandler 메소드나 @ControllerAdvice 메소드도 물론 사용 할 수 있으며 얘네들로 처리되지 않은 예외들은 ErrorController에 의해 처리된다.

### 27.1.13 CORS Support *
 - Cross-origin resource sharing 지원
 
## 27.2 The “Spring WebFlux Framework” *
 - Spring WebFlux는 스프링 프레임워크 5.0에서 소개된 새로운 리엑티브 웹 프레임워크다.
 - 사용하기 위해서는 spring-boot-starter-webflux 모듈을 추가해주면 되는데, 이때 spring-boot-starter-web 모듈과 같이 사용하게 된다면 WebFlux가 아닌 Spring MVC로 자동 설정이 된다.
 - 톰캣이 아니고 내장 netty로 띄운다.
 - Spring WebFlux comes in two flavors: functional and annotation-based.

### 27.2.2 HTTP Codecs with HttpMessageReaders and HttpMessageWriters
 - Spring Web MVC Framework의 HttpMessageConverters와 비슷
 
### 27.2.3 Static Content *
 - 기본적으로 스프링 부트는 ServletContext의 root 클래스패스 안의 /static, /public, /resources 디렉토리에 있는 정적 content를 서빙해준다.
 - WebFluxConfigurer의 addResourceHandlers를 오버라이드 하여 커스터마이징 할 수 있다.
 - 내용이 Spring MVC의 Static Content와 같다.
 
### 27.2.4 Template Engines
 - FreeMarker, Thymeleaf, Mustache 이와 같은 템플릿 중 하나를 사용 한다면 "src/main/resources/templates" 경로 밑에서 자동으로 템플릿을 찾아준다.
 
### 27.2.5 Error Handling
 - 내용이 Spring MVC와 비슷
 
### 27.2.6 Web Filters *
 - Spring WebFlux는 HTTP request-response 사이의 필터링을 하기 위해 WebFilter를 제공한다. 여러 필터가 사용 될 수 있으며 이는 @Order 어노테이션으로 순서를 정해줄 수 있다.
 
## 27.3 JAX-RS and Jersey
 - spring-boot-starter-jersey ........... pass 
 
## 27.4 Embedded Servlet Container Support
 - 스프링부트는 embedded Tomcat, Jetty, and Undertow server를 제공한다. 기본적으로 8080포트를 통해 http요청을 처리한다.
 
### 27.4.1 Servlets, Filters, and listeners
 - 임베디드 서블릿 컨테이너를 사용할때, 스프링빈을 사용해서 등록하거나 서블릿 컴포넌트를 스캐닝 하는 방식으로 서블릿 필터 리스너를 등록할 수 있다.
 - 또는 ServletRegistrationBean, FilterRegistrationBean, ServletListenerRegistrationBean 클래스를 사용할 수 있다.
 - 기본적으로 만약에 컨텍스트가 싱글 서블릿일 경우 모든 요청이 그 서블릿에 매핑되지만 여러개라면 bean 이름이 path의 prefix로 사용된다.
 
### 27.4.2 Servlet Context Initialization
 - 임베디드 서블릿 컨테이너는 서블릿 3.0이상에서 사용하는 ServletContainerInitializer를 사용하지 않는다. 또는 WebApplicationInitializer를 사용하지 않는다. 쟤네를 사용하면 war안에서 사용되는 서드파티 어플리케이션이 스프링 부트 어플리케이션에 영향을 줄 수도 있다.
 - servlet context initialization이 필요하다면 ServletContextInitializer로 구현한 bean을 등록해야 한다.
 - 직접 빈을 등록하지 않고 스캐닝을 할 수 있는데, 임베디드 컨테이너를 쓸때는 @WebServlet, @WebFilter, @WebListener 어노테이션을 쓰면 @ServletComponentScan 어노테이션으로 스캐닝을 할 수 있다. standalone 컨테이너 에서는 소용이 없다. (standalone 컨테이너 = 임베디드 컨테이너가 아닌 경우, 예전같이 톰켓 인스턴스 따로 띄워서 하던 그런 경우)

### 27.4.3 The ServletWebServerApplicationContext
 - 임베디드 서블릿 컨테이너를 지원하기 위해 여러가지 타입의 ApplicationContext를 사용하는데, ServletWebServerApplicationContext가 WebApplicationContext의 특별한 타입이다. 보통은 TomcatServletWebServerFactory, JettyServletWebServerFactory, UndertowServletWebServerFactory가 자동 설정 되어있다. 이들중 하나가 ServletWebServerFactory를 사용해서 ServletWebServerApplicationContext를 만든다.
 
### 27.4.4 Customizing Embedded Servlet Containers
 - 보통 서블릿 컨테이너 설정은 Spring Environment properties를 사용해서 설정하면 되는데, application.properties 파일에서 우리가 알다시피 해주면 된다.
 - 가장 흔한 설정은 네트워크 세팅에서 포트나 서버주소, 세션세팅, 세션 쿠키 설정 등등 이런거 할 수 있다.
 - Programmatic Customization 도 가능한데 (예제 소스 참고)
 - 동시설정을 하면 어떻게 되느냐 Programmatic Customization이 다 이긴다.
 - 만약 위 두개보다 더 많은걸 설정하고 싶다면 TomcatServletWebServerFactory를 직접 커스터마이징 하면 되는데 이렇게 할바에야 스프링부트를 안쓰면 될꺼같다는 생각이 든다.

## 28. Security
 - 스프링 시큐리티가 클래스패스에 있으면 웹 애플리케이션은 기본적으로 보안처리가 된다. httpBasic 을 사용할지 formLogin을 사용할지를 정할 때 Spring Security’s content-negotiation strategy에 의존한다.
 - method-level의 security를 웹애플리케이션에 추가하려면 @EnableGlobalMethodSecurity을 원하는 세팅과 함께 사용하면 된다.
 - 스프링 시큐리티의 레퍼런스 가이드가 있다는데 일단은 한번 눌러만보자.
 - UserDetailsService에서 기본적으로 사용자를 하나 가지고 있는데 name은 user로 되어있고 패스워드는 랜덤인데 이건 어플리케이션이 실행 될 때 인포레벨로 출력된다.
 - properties에서 spring.security.user.name and spring.security.user.password를 설정 할 수도 있다.
 - 기본적으로 얻을 수 있는 것으로는
  - in memory store를 쓰는 단일 유저객체가 들어있는 UserDetailsService (ReactiveUserDetailsService)가 빈에 등록된다.
  - 어플리케이션의 모든 url이 시큐어하게 감싸진다.
  - DefaultAuthenticationEventPublisher

### 28.1 MVC Security
 - 기본 시큐리티 설정은 SecurityAutoConfiguration과 UserDetailsServiceAutoConfiguration에서 구현 하고 있다.
 - SecurityAutoConfiguration은 웹 시큐리티에서는 SpringBootWebSecurityConfiguration을 import하고 웹 어플리케이션이 아닌 시큐리티에서는 UserDetailsServiceAutoConfiguration을 import한다.
 - 시큐리티 설정을 완전히 끄려면 WebSecurityConfigurerAdapter 타입의 빈을 추가하면 되는데 이때 UserDetailsService 설정이나 Actuator’s security는 꺼지지 않는다. 이걸 끄고 싶으면 UserDetailsService, AuthenticationProvider, AuthenticationManager 빈을 추가해줘야 한다.

### 28.2 WebFlux Security
 - Spring MVC applications과 똑같이 spring-boot-starter-security 디펜던시를 먹이면 된다.
 - ReactiveSecurityAutoConfiguration에 기본 설정들이 있으며 웹 시큐리티에서는 WebFluxSecurityConfiguration을 import하고 웹 어플리케이션이 아닌 시큐리티에서는 UserDetailsServiceAutoConfiguration import한다.
 - 시큐리티 설정을 완전히 끄려면 WebFilterChainProxy 타입의 빈을 등록하면 된다. 역시나 이렇게 한다고 해도 UserDetailsService 설정이나 Actuator’s security는 꺼지지 않는다. 그래서 ReactiveUserDetailsService빈이나 ReactiveAuthenticationManager빈을 등록 해야한다.

### 28.3 OAuth2
 - OAuth2는 널리 사용되는 인증 프레임웤이다.
 
### 28.3.1 Client
 - spring-security-oauth2-client가 클래스패스에 있으면 OAuth2 클라이언트를 셋업하기 위한 몇가지 오토 컨피규레이션을 제공 받을 수 있다. 이 설정은 OAuth2ClientProperties에 들어가는 여러 프로퍼티들을 쓸 수 있게 해준다.
 - prefix : spring.security.oauth2.client
 - 기본적으로 스프링 시큐리의 OAuth2LoginAuthenticationFilter는 /login/oauth2/code/* 에 매핑되는 url만 처리한다. 만약 redirect-uri-template을 커스터마이징 하고자 한다면 이를 따로 설정 해줘야한다 (레퍼런스 소스)

### 28.4 Actuator Security
 - 보안을 고려하여 /health and /info를 뺀 모든 actuator는 기본적으로 시큐리티 뒤로 숨는데 actuator를 활성화 하고 싶으면 management.endpoints.web.exposure.include 이 프로퍼티 뒤에 활성화 할 엔드포인트를 적어주면 된다.

### 28.4.1 Cross Site Request Forgery Protection
 - 스프링 시큐리티는 기본적으로 CSRF Protection기능이 켜져있다.
 - CSRF Protection을 하는 방법은 서버에서 보내준 특정한 토큰값을 요청 보낼때 같이 보내는데 서버에서는 이 토큰을 보고 자신이 만든 페이지에서 들어온 요청이라는걸 인지하는 방식이다.