# SpringBoot2Tutorial


# 33. Calling REST Services with RestTemplate


* **RestTemplate**은 보통 customize를 많이 하므로 auto configure 미제공
* 하지만, **RestTemplateBuilder** 로 auto configure 지원. 필요할 때 RestTemplate 인스턴스를 생성 가능 => 실습


## 33.1 RestTemplate Customization
* 3가지 방식 지원
1. customization 범위을 최소화 화려면 **RestTemplateBuilder**의 method를 호출하는 방식으로 customization. customization은 builder 사용시에만 영향
2. application 수준으로 하려면 **RestTemplateCustomizer** 사용 => 실습
3. **RestTemplateBuilder** 빈을 생성해서 자체적으로 사용 => auto-configuration off

# 34. Calling REST Services with WebClient
* Spring WebFlux를 사용한다면, **WebClient**를 사용해서 rest service call 가능 => 실습
* RestTemplate에 비해 fully reactive
* WebClient.create() 로 자체 client 생성 가능

## 34.1 RestTemplate Customization
* 3가지 방식 지원
1. customization 범위을 최소화 화려면 **WebClient.Builder **의 method를 호출하는 방식으로 customization
2. application 수준으로 하려면 **WebClientCustomizer** 사용
3. **WebClient.create()**로 빈생성해서 자체적으로 사용 => auto-configuration off



# 43. Testing


## 43.1 Test Scope Dependencies

** spring-boot-starter-test 는 다음 라이브러리들을 포함 
- JUnit: The de-facto standard for unit testing Java applications.
- Spring Test & Spring Boot Test: Utilities and integration test support for Spring Boot applications.
- AssertJ: A fluent assertion library.
- Hamcrest: A library of matcher objects (also known as constraints or predicates).
- Mockito: A Java mocking framework.
- JSONassert: An assertion library for JSON.
- JsonPath: XPath for JSON.


## 43.3 Testing Spring Boot Applications
* http://meetup.toast.com/posts/124

* spring-boot-test는 @SpringBootTest라는 어노테이션을 제공합니다. 이 어노테이션을 사용하면 테스트에 사용할 ApplicationContext를 쉽게 생성하고 조작할 수 있습니다.
* 기존 spring-test에서 사용하던 @ContextConfiguration의 발전된 기능이라고 할 수 있습니다.
* @SpringBootTest는 매우 다양한 기능을 제공합니다. 전체 빈 중 특정 빈을 선택하여 생성한다든지, 특정 빈을 Mock으로 대체한다든지, 테스트에 사용할 프로퍼티 파일을 선택하거나 특정 속성만 추가한다든지, 특정 Configuration을 선택하여 설정할 수도 있습니다. 또한, 주요 기능으로 테스트 웹 환경을 자동으로 설정해주는 기능이 있습니다.
* 앞에서 언급한 다양한 기능들을 사용하기 위해서 첫 번째로 가장 중요한 것은 @SpringBootTest 기능은 반드시 @RunWith(SpringRunner.class)와 함께 사용해야 한다는 점입니다.


* Web Environment test

    - 앞에서 언급했듯이 @SpringBootTest 어노테이션을 사용하면 손쉽게 웹 테스트 환경을 구성할 수 있습니다. @SpringBootTest의 webEnvironment 파라미터를 이용해서 손쉽게 웹 테스트 환경을 선택할 수 있습니다. 제공하는 설정값은 아래와 같습니다.
  
        1. MOCK
            - WebApplicationContext를 로드하며 내장된 서블릿 컨테이너가 아닌 Mock 서블릿을 제공합니다. @AutoConfigureMockMvc 어노테이션을 함께 사용하면 별다른 설정 없이 간편하게 MockMvc를 사용한 테스트를 진행할 수 있습니다.
        2. RANDOM_PORT
            - EmbeddedWebApplicationContext를 로드하며 실제 서블릿 환경을 구성합니다. 생성된 서블릿 컨테이너는 임의의 포트는 listen합니다.
        3. DEFINED_PORT
            - RAMDOM_PORT와 동일하게 실제 서블릿 환경을 구성하지만, 포트는 애플리케이션 프로퍼티에서 지정한 포트를 listen합니다(application.properties 또는 application.yml에서 지정한 포트).
        4. NONE
            - 일반적인 ApplicationContext를 로드하며 아무런 서블릿 환경을 구성하지 않습니다.


## 43.3.9 Auto-configured Spring WebFlux Tests
* @WebFluxTest annotation을 이용해서 Spring WebFlux 테스트 가능
* @WebFluxTest auto-configures the Spring WebFlux infrastructure and limits scanned beans to @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, and WebFluxConfigurer. Regular @Component beans are not scanned when the @WebFluxTest annotation is used

## 43.3.10 Auto-configured Data JPA Tests / 43.3.17 Auto-configured REST Clients
* http://meetup.toast.com/posts/124



# 47.3 Kotlin API
* http://hyper-cube.io/2017/11/27/spring5-with-kotlin/
* https://academy.realm.io/kr/posts/kotlin-does-java-droidcon-boston-2017-gonda/
