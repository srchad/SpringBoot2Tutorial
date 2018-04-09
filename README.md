# SpringBoot2Tutorial

# Study 1. 1~12
Overview and Install
- Spring Boot 2.0.1.BUILD-SNAPSHOT requires Java 8 or 9 and Spring Framework 5.0.5.BUILD-SNAPSHOT or above. Explicit build support is provided for Maven 3.2+ and Gradle 4.

# Study 2. 13~26

##Part III. Using Spring Boot

###13. Build Systems

####13.2 Maven
Maven users can inherit from the spring-boot-starter-parent project to obtain sensible defaults.

#####13.2.1 Inheriting the Starter Parent
```
<!-- Inherit defaults from Spring Boot -->
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.0.1.BUILD-SNAPSHOT</version>
</parent>
```

If you do not want to use the spring-boot-starter-parent, you can still keep the benefit of the dependency management (but not the plugin management) by using a scope=import dependency, as follows:
```
<dependencyManagement>
		<dependencies>
		<dependency>
			<!-- Import dependency management from Spring Boot -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-dependencies</artifactId>
			<version>2.0.1.BUILD-SNAPSHOT</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

##### 13.2.3 Using the Spring Boot Maven Plugin
```
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

###14. Struncturing Your Code
Spring Boot does not require any specific code layout to work. However, there are some best practices that help.

####14.1 "default" package
When a class does not include a package declaration, it is considered to be in the “default package”. The use of the “default package” is generally discouraged and should be avoided.
It can cause particular problems for Spring Boot applications that use the **@ComponentScan, @EntityScan, or @SpringBootApplication** annotations, since every class from every jar is read.

####14.2 Locating the Main Applicataion Class
We generally recommend that you locate your main application class in a root package above other classes. 
The **@EnableAutoConfiguration annotation is often placed on your main class**, and it implicitly defines a base “search package” for certain items. 
For example, if you are writing a JPA application, the package of the @EnableAutoConfiguration annotated class is used to search for @Entity items.

Using a root package also lets the @ComponentScan annotation be used without needing to specify a basePackage attribute. You can also use the @SpringBootApplication annotation if your main class is in the root package.

###15. Configuration Classes

####15.1 Importing Additional Configuration Classes
You need not put all your **@Configuration** into a single class. The **@Import** annotation can be used to import additional configuration classes. 
Alternatively, you can use **@ComponentScan to automatically pick up all Spring components, including @Configuration classes.** -> auto-configruation?

####15.2 Importing XML Configuration
You can then use an **@ImportResource annotation to load XML configuration files.**

###16. Auto-configuration
You need to opt-in to auto-configuration by adding the @EnableAutoConfiguration or @SpringBootApplication annotations to one of your @Configuration classes.

####16.2 Disabling Specific Auto-configuration Classes
```
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
```

###17. Spring Beans and Dependency Injection
You are free to use any of the standard Spring Framework techniques to define your beans and their injected dependencies. For simplicity, we often find that using **@ComponentScan** (to find your beans) and using **@Autowired** (to do constructor injection) works well.
```
@Service
public class DatabaseAccountService implements AccountService {

	private final RiskAssessor riskAssessor;

	@Autowired
	public DatabaseAccountService(RiskAssessor riskAssessor) {
		this.riskAssessor = riskAssessor;
	}

	// ...

}
```

###18. Using the @SpringBootApplication Annotation
The @SpringBootApplication annotation is equivalent to using **@Configuration, @EnableAutoConfiguration, and @ComponentScan** with their default attributes, as shown in the following example:

```
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
```

####19.3 Using the Maven Plugin
```
mvn spring-boot:run
export MAVEN_OPTS=-Xmx1024m
```

####19.5 Hot Swapping
The spring-boot-devtools module also includes support for quick application restarts.

###20. Developer Tools
Spring Boot includes an additional set of tools that can make the application development experience a little more pleasant. The spring-boot-devtools module can be included in any project to provide additional development-time features. 

* spring-boot-devtools

The spring-boot-devtools module can be included in any project to provide additional development-time features
Applications that use spring-boot-devtools automatically restart whenever files on the classpath change.
By default, any entry on the classpath that points to a folder is monitored for changes. Note that certain resources, such as static assets and view templates, do not need to restart the application.

## Part IV. Spring Boot features

### 23. SpringApplication
The SpringApplication class provides a convenient way to bootstrap a Spring application that is started from a main() method.
you can delegate to the static **SpringApplication.run** method

```
public static void main(String[] args) {
	SpringApplication.run(MySpringConfiguration.class, args);
}
```

#### 23.1 Startup Failure
If your application fails to start, registered FailureAnalyzers get a chance to provide a dedicated error message and a concrete action to fix the problem. 
[failure guide](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-failure-analyzer
)

If no failure analyzers are able to handle the exception, you can still display the full conditions report to better understand what went wrong. To do so, you need to enable the debug property or enable DEBUG logging for org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener.

#### 23.2 Customizing the Banner
The banner that is printed on start up can be changed by adding a **banner.txt** file to your classpath or by setting the spring.banner.location property to the location of such a file. 
Table 23.1. Banner variables

#### 23.4 Fluent Builder API
The **SpringApplicationBuilder** lets you chain together multiple method calls and includes parent and child methods that let you create a hierarchy, as shown in the following example:

```
new SpringApplicationBuilder()
		.sources(Parent.class)
		.child(Application.class)
		.bannerMode(Banner.Mode.OFF)
		.run(args);
```

> There are some restrictions when creating an ApplicationContext hierarchy. For example, Web components must be contained within the child context, and the same Environment is used for both parent and child contexts.

#### 23.5 Application Events and Listeners
In addition to the usual Spring Framework events, such as ContextRefreshedEvent, a SpringApplication sends some additional application events.
You can register them with the **SpringApplication.addListeners(…​)** method or the **SpringApplicationBuilder.listeners(…​)** method.

Application events are sent in the following order, as your application runs:

- An **ApplicationStartingEvent** is sent at the start of a run but before any processing, except for the registration of listeners and initializers.
- An **ApplicationEnvironmentPreparedEvent** is sent when the Environment to be used in the context is known but before the context is created.
- An **ApplicationPreparedEvent** is sent just before the refresh is started but after bean definitions have been loaded.
- An **ApplicationStartedEvent** is sent after the context has been refreshed but before any application and command-line runners have been called.
- An **ApplicationReadyEvent** is sent after any application and command-line runners have been called. It indicates that the application is ready to service requests.
- An **ApplicationFailedEvent** is sent if there is an exception on startup.

If you want those listeners to be registered automatically, regardless of the way the application is created, 
you can add a META-INF/spring.factories file to your project and reference your listener(s) by using the org.springframework.context.ApplicationListener key, 
```
// in application.properties.. spring.factories readonly T.T
org.springframework.context.ApplicationListener=com.example.project.MyListener
```

#### 23.6 Web Environment
A SpringApplication attempts to create the right type of ApplicationContext on your behalf. The algorithm used to determine a WebEnvironmentType is fairly simple:

- If Spring MVC is present, an **AnnotationConfigServletWebServerApplicationContext** is used
- If Spring MVC is not present and Spring WebFlux is present, an **AnnotationConfigReactiveWebApplicationContext** is used
- Otherwise, **AnnotationConfigApplicationContext** is used

It is also possible to take complete control of the ApplicationContext type that is used by calling setApplicationContextClass(…​).

> It is often desirable to call setWebApplicationType(WebApplicationType.NONE) when using SpringApplication within a JUnit test.

#### 23.7 Accessing Application Arguments
If you need to access the application arguments that were **passed to SpringApplication.run(…​)**, you can inject a **org.springframework.boot.ApplicationArguments** bean. 
The ApplicationArguments interface provides access to both the raw String[] arguments as well as parsed option and non-option arguments, as shown in the following example:

```
@Component
public class MyBean {

	@Autowired
	public MyBean(ApplicationArguments args) {
		boolean debug = args.containsOption("debug");
		List<String> files = args.getNonOptionArgs();
		// if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
	}

}
```

#### 23.8 Using the ApplicationRunner or CommandLineRunner
If you need to run some specific code once the SpringApplication has started, you can implement the ApplicationRunner or CommandLineRunner interfaces.
Both interfaces work in the same way and offer a single run method, which is called just before SpringApplication.run(…​) completes.
The CommandLineRunner interfaces provides access to application arguments as a simple string array, whereas the ApplicationRunner uses the ApplicationArguments interface discussed earlier. 

If several CommandLineRunner or ApplicationRunner beans are defined that must be called in a specific order, you can additionally implement the **org.springframework.core.Ordered** interface or use the org.springframework.core.annotation.Order annotation.

#### 23.9 Application Exit
Each SpringApplication registers a shutdown hook with the JVM to ensure that the ApplicationContext closes gracefully on exit.
In addition, beans may implement the org.springframework.boot.ExitCodeGenerator interface if they wish to return a specific exit code when SpringApplication.exit() is called. 

Also, the ExitCodeGenerator interface may be implemented by exceptions. When such an exception is encountered, Spring Boot returns the exit code provided by the implemented getExitCode() method.

#### 23.10 Admin Features
It is possible to enable admin-related features for the application by specifying the spring.application.admin.enabled property. 

### 24. Externalized Configuration

#### 24.1 Configuring Random Values
The RandomValuePropertySource is useful for injecting random values
```
my.secret=${random.value}
my.number=${random.int}
my.bignumber=${random.long}
my.uuid=${random.uuid}
my.number.less.than.ten=${random.int(10)}
my.number.in.range=${random.int[1024,65536]}
```

#### 24.2 Accessing Command Line Properties
By default, SpringApplication converts any command line option arguments (that is, arguments starting with --, such as --server.port=9000) to a property and adds them to the Spring Environment. As mentioned previously, command line properties always take precedence over other property sources.

If you do not want command line properties to be added to the Environment, you can disable them by using SpringApplication.setAddCommandLineProperties(false).

#### 24.3 Application Property Files

SpringApplication loads properties from application.properties files in the following locations and adds them to the Spring Environment:

1. A /config subdirectory of the current directory
2. The current directory
3. A classpath /config package
4. The classpath root

If you do not like application.properties as the configuration file name, you can switch to another file name by specifying a spring.config.name environment property

> java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties

Config locations are searched in reverse order. By default, the configured locations are classpath:/,classpath:/config/,file:./,file:./config/. The resulting search order is the following:

1. file:./config/
2. file:./
3. classpath:/config/
4. classpath:/

#### 24.4 Profile-specific Properties
In addition to application.properties files, profile-specific properties can also be defined by using the following naming convention: application-{profile}.properties. 

#### 24.5 Placeholders in Properties

### 24.6 Using YAML Instead of Properties

#### 24.6.1 Loading YAML
Spring Framework provides two convenient classes that can be used to load YAML documents.

YamlMapFactoryBean 
YamlPropertiesFactoryBean 

```
@ConfigurationProperties(prefix="my")
public class Config {

   private List<String> servers = new ArrayList<String>();

   public List<String> getServers() {
      return this.servers;
   }
}
```

#### 24.6.2 Exposing YAML as Properties in the Spring Environment
The YamlPropertySourceLoader class can be used to expose YAML as a PropertySource in the Spring Environment

#### 24.6.3 Multi-profile YAML Documents

### 24.7 Type-safe Configuration Properties
Using the @Value("${property}") annotation to inject configuration properties can sometimes be cumbersome, especially if you are working with multiple properties or your data is hierarchical in nature.

Even if the preceding configuration creates a regular bean for AcmeProperties, we recommend that @ConfigurationProperties only deal with the environment and, in particular, does not inject other beans from the context. Having said that, the @EnableConfigurationProperties annotation is also automatically applied to your project so that any existing bean annotated with @ConfigurationProperties is configured from the Environment. You could shortcut MyConfiguration by making sure AcmeProperties is already a bean, as shown in the following example:

```
# application.yml

acme:
   remote-address: 192.168.1.1
   security:
      username: admin
      roles:
        - USER
        - ADMIN

# additional configuration as required
```

```
@Component
@ConfigurationProperties(prefix="acme")
public class AcmeProperties {

   // ... see the preceding example

}
```

To work with @ConfigurationProperties beans, you can inject them in the same way as any other bean

```
@Service
public class MyService {

   private final AcmeProperties properties;

   @Autowired
   public MyService(AcmeProperties properties) {
       this.properties = properties;
   }

    //...

   @PostConstruct
   public void openConnection() {
      Server server = new Server(this.properties.getRemoteAddress());
      // ...
   }
}
```

#### 24.7.1 Third-party Configuration

As well as using @ConfigurationProperties to annotate a class, you can also use it on public @Bean methods. Doing so can be particularly useful when you want to bind properties to third-party components that are outside of your control.

```
@ConfigurationProperties(prefix = "another")
@Bean
public AnotherComponent anotherComponent() {
   ...
}
```

#### 24.7.2 Relaxed Binding
Spring Boot uses some relaxed rules for binding Environment properties to @ConfigurationProperties beans, so there does **not need to be an exact match ** between the Environment property name and the bean property name. 

#### 24.7.3 Properties Conversion
@DurationUnit

#### 24.7.4 @ConfigurationProperties Validation
@Validated
- @Valid
- @NotNull
- @NotEmpty

#### 24.7.5 @ConfigurationProperties vs. @Value
relaxed binding y/n
meta-data support y/n
spel evaluation n/y

### 25. Profiles
Spring Profiles provide a way to segregate parts of your application configuration and make it be available only in certain environments. Any @Component or @Configuration can be marked with @Profile to limit when it is loaded.

#### 25.1 Adding Active Profiles
 it is useful to have profile-specific properties that add to the active profiles rather than replace them. The spring.profiles.include property can be used to unconditionally add active profiles. 

#### 25.2 Programmatically Setting Profiles
You can programmatically set active profiles by calling SpringApplication.setAdditionalProfiles(… ) before your application runs. It is also possible to activate profiles by using Spring’s ConfigurableEnvironment interface.

#### 25.3 Profile-specific Configuration Files

### 26. Logging

#### 26.1 Log Format
Log Level: ERROR, WARN, INFO, DEBUG, or TRACE.

#### 26.2 Console Output
 java -jar myapp.jar --debug

##### 26.2.1 Color-coded Output
%clr(%5p)

### 26.3 File Output
By default, Spring Boot logs only to the console and does not write log files. If you want to write log files in addition to the console output, you need to set a logging.file or logging.path property (for example, in your application.properties).

The following table shows how the logging.* properties can be used together:

### 26.4 Log Levels
logging.level.<logger-name>=<level>

```
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```

### 26.5 Custom Log Configuration
Logging System   Customization
Logback : logback-spring.xml, logback-spring.groovy, logback.xml, or logback.groovy
Log4j2 : log4j2-spring.xml or log4j2.xml
JDK (Java Util Logging) : logging.properties

### 26.6 Logback Extensions
logback-spring.xml
logging.config

#### 26.6.1 Profile-specific Configuration
The <springProfile> tag lets you optionally include or exclude sections of configuration based on the active Spring profiles.

#### 26.6.2 Environment Properties

# Study 3. 27~28

## 27. Developing Web Applications
You can create a self-contained HTTP server by using embedded Tomcat, Jetty, Undertow, or Netty. 
Most web applications use the spring-boot-starter-web module to get up and running quickly. 
You can also choose to build reactive web applications by using the spring-boot-starter-webflux module.

### 27.1 The “Spring Web MVC Framework”
Spring MVC lets you create special @Controller or @RestController beans to handle incoming HTTP requests. Methods in your controller are mapped to HTTP by using @RequestMapping annotations.

#### 27.1.1 Spring MVC Auto-configuration

#### 27.1.2 HttpMessageConverters
Spring MVC uses the **HttpMessageConverter** interface to convert HTTP requests and responses. 
Sensible defaults are included out of the box. 
For example, objects can be automatically converted to JSON (by using the Jackson library) or XML (by using the Jackson XML extension, if available, or by using JAXB if the Jackson XML extension is not available). By default, strings are encoded in UTF-8.

If you need to add or customize converters, you can use Spring Boot’s **HttpMessageConverters**.

#### 27.1.3 Custom JSON Serializers and Deserializers
You can use the **@JsonComponent** annotation directly on JsonSerializer or JsonDeserializer implementations. 
You can also use it on classes that contain **serializers/deserializers as inner classes**.

#### 27.1.4 MessageCodesResolver
Spring MVC has a strategy for generating error codes for rendering error messages from binding errors: MessageCodesResolver.
If you set the **spring.mvc.message-codes-resolver.format** property PREFIX_ERROR_CODE or POSTFIX_ERROR_CODE, Spring Boot creates one for you (see the enumeration in DefaultMessageCodesResolver.Format).

#### 27.1.5 Static Content
By default, Spring Boot serves static content from a directory called /static (or /public or /resources or /META-INF/resources) in the classpath or from the root of the ServletContext. 
It uses the **ResourceHttpRequestHandler** from Spring MVC so that you can modify that behavior by **adding your own WebMvcConfigurer and overriding the addResourceHandlers method**.

spring.mvc.static-path-pattern

spring.resources.static-locations

spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**

spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**
spring.resources.chain.strategy.fixed.enabled=true
spring.resources.chain.strategy.fixed.paths=/js/lib/
spring.resources.chain.strategy.fixed.version=v12

#### 27.1.8 Path Matching and Content Negotiation
Spring Boot chooses to disable suffix pattern matching by default, which means that requests like "GET /projects/spring-boot.json" won’t be matched to @GetMapping("/projects/spring-boot") mappings. 
This is considered as a best practice for Spring MVC applications. 
This feature was mainly useful in the past for HTTP clients which did not send proper "Accept" request headers; 
we needed to make sure to send the correct Content Type to the client. Nowadays, Content Negotiation is much more reliable.

There are other ways to deal with HTTP clients that don’t consistently send proper "Accept" request headers. 
Instead of using suffix matching, we can use a query parameter to ensure that requests like "GET /projects/spring-boot?format=json" will be mapped to @GetMapping("/projects/spring-boot"):

```
spring.mvc.contentnegotiation.favor-parameter=true

# We can change the parameter name, which is "format" by default:
# spring.mvc.contentnegotiation.parameter-name=myparam

# We can also register additional file extensions/media types with:
spring.mvc.contentnegotiation.media-types.markdown=text/markdown
```

If you understand the caveats and would still like your application to use suffix pattern matching, the following configuration is required:
```
spring.mvc.contentnegotiation.favor-path-extension=true

# You can also restrict that feature to known extensions only
# spring.mvc.pathmatch.use-registered-suffix-pattern=true

# We can also register additional file extensions/media types with:
# spring.mvc.contentnegotiation.media-types.adoc=text/asciidoc
```

#### 27.1.9 ConfigurableWebBindingInitializer
Spring MVC uses a **WebBindingInitializer** to initialize a WebDataBinder for a particular request. 

#### 27.1.10 Template Engines
- FreeMarker
- Groovy
- Thymeleaf
- Mustache

#### 27.1.11 Error Handling
By default, Spring Boot provides an /error mapping that handles all errors in a sensible way, and it is registered as a “global” error page in the servlet container. 
For machine clients, it produces a JSON response with details of the error, the HTTP status, and the exception message. 
For browser clients, there is a “whitelabel” error view that renders the same data in HTML format (to customize it, add a View that resolves to error). 
To replace the default behavior completely, you can implement ErrorController and register a bean definition of that type or add a bean of type ErrorAttributes to use the existing mechanism but replace the contents.

You can also define a class annotated with **@ControllerAdvice** to customize the JSON document to return for a particular controller and/or exception type, as shown in the following example:

##### Mapping Error Pages outside of Spring MVC
For applications that do not use Spring MVC, you can use the ErrorPageRegistrar interface to directly register ErrorPages. 
This abstraction works directly with the underlying embedded servlet container and works even if you do not have a Spring MVC DispatcherServlet.

### 27.2 The “Spring WebFlux Framework”
Spring WebFlux comes in two flavors: functional and annotation-based. The annotation-based one is quite close to the Spring MVC model.

```
@RestController
@RequestMapping("/users")
public class MyRestController {

	@GetMapping("/{user}")
	public Mono<User> getUser(@PathVariable Long user) {
		// ...
	}

	@GetMapping("/{user}/customers")
	public Flux<Customer> getUserCustomers(@PathVariable Long user) {
		// ...
	}

	@DeleteMapping("/{user}")
	public Mono<User> deleteUser(@PathVariable Long user) {
		// ...
	}

}
```

“WebFlux.fn”, the functional variant, separates the routing configuration from the actual handling of the requests.
```
@Configuration
public class RoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
		return route(GET("/{user}").and(accept(APPLICATION_JSON)), userHandler::getUser)
				.andRoute(GET("/{user}/customers").and(accept(APPLICATION_JSON)), userHandler::getUserCustomers)
				.andRoute(DELETE("/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
	}

}

@Component
public class UserHandler {

	public Mono<ServerResponse> getUser(ServerRequest request) {
		// ...
	}

	public Mono<ServerResponse> getUserCustomers(ServerRequest request) {
		// ...
	}

	public Mono<ServerResponse> deleteUser(ServerRequest request) {
		// ...
	}
}
```

To get started, add the **spring-boot-starter-webflux** module to your application.

#### 27.2.1 Spring WebFlux Auto-configuration
The auto-configuration adds the following features on top of Spring’s defaults:

Configuring codecs for HttpMessageReader and HttpMessageWriter instances (described later in this document).
Support for serving static resources, including support for WebJars (described later in this document).

#### 27.2.2 HTTP Codecs with HttpMessageReaders and HttpMessageWriters
Spring WebFlux uses the HttpMessageReader and HttpMessageWriter interfaces to convert HTTP requests and responses. 
They are configured with CodecConfigurer to have sensible defaults by looking at the libraries available in your classpath.

Spring Boot applies further customization by using CodecCustomizer instances. For example, spring.jackson.* configuration keys are applied to the Jackson codec.

#### 27.2.3 Static Content
By default, Spring Boot serves static content from a directory called /static (or /public or /resources or /META-INF/resources) in the classpath. 
It uses the ResourceWebHandler from Spring WebFlux so that you can modify that behavior by adding your own WebFluxConfigurer and overriding the addResourceHandlers method.

you can tune that by setting the spring.webflux.static-path-pattern property. 
```
spring.webflux.static-path-pattern=/resources/**
```

#### 27.2.4 Template Engines
Spring Boot includes auto-configuration support for the following templating engines:

FreeMarker
Thymeleaf
Mustache

#### 27.2.5 Error Handling
Spring Boot provides a **WebExceptionHandler** that handles all errors in a sensible way. Its position in the processing order is immediately before the handlers provided by WebFlux, which are considered last.

The first step to customizing this feature often involves using the existing mechanism but replacing or augmenting the error contents. For that, you can add a bean of type ErrorAttributes.

#### 27.2.6 Web Filters
Spring WebFlux provides a WebFilter interface that can be implemented to filter HTTP request-response exchanges. WebFilter beans found in the application context will be automatically used to filter each exchange.

### 27.3 JAX-RS and Jersey
If you prefer the JAX-RS programming model for REST endpoints, you can use one of the available implementations instead of Spring MVC. Jersey 1.x and Apache CXF work quite well out of the box if you register their Servlet or Filter as a @Bean in your application context.
To get started with Jersey 2.x, include the **spring-boot-starter-jersey** as a dependency and then you need one **@Bean** of type **ResourceConfig** in which you register all the endpoints.

```
@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(Endpoint.class);
	}
}
```
For more advanced customizations, you can also register an arbitrary number of beans that implement ResourceConfigCustomizer.

All the registered endpoints should be **@Components** with HTTP resource annotations (@GET and others).
```
@Component
@Path("/hello")
public class Endpoint {

	@GET
	public String message() {
		return "Hello";
	}

}
```

### 27.4 Embedded Servlet Container Support
Spring Boot includes support for embedded Tomcat, Jetty, and Undertow servers. 
Most developers use the appropriate “Starter” to obtain a fully configured instance. 
By default, the embedded server listens for HTTP requests on port 8080.

#### 27.4.1 Servlets, Filters, and listeners
Any Servlet, Filter, or servlet *Listener instance that is a Spring bean is registered with the embedded container. 
This can be particularly convenient if you want to refer to a value from your application.properties during configuration.

#### 27.4.2 Servlet Context Initialization
When using an embedded container, automatic registration of classes annotated with **@WebServlet**, **@WebFilter**, and **@WebListener** can be enabled by using **@ServletComponentScan**.

#### 27.4.3 The ServletWebServerApplicationContext
**ServletWebServerApplicationContext** is a special type of WebApplicationContext that bootstraps itself by searching for a single ServletWebServerFactory bean.
Usually a TomcatServletWebServerFactory, JettyServletWebServerFactory, or UndertowServletWebServerFactory has been auto-configured.

#### 27.4.4 Customizing Embedded Servlet Containers
Common servlet container settings can be configured by using Spring Environment properties. Usually, you would define the properties in your application.properties file.

- Network settings: Listen port for incoming HTTP requests (server.port), interface address to bind to server.address, and so on.
- Session settings: Whether the session is persistent (server.servlet.session.persistence), session timeout (server.servlet.session.timeout), location of session data (server.servlet.session.store-dir), and session-cookie configuration (server.servlet.session.cookie.*).
- Error management: Location of the error page (server.error.path) and so on.
- SSL
- HTTP compression

If you need to programmatically configure your embedded servlet container, you can register a Spring bean that implements the **WebServerFactoryCustomizer** interface. 
If the preceding customization techniques are too limited, you can register the TomcatServletWebServerFactory, JettyServletWebServerFactory, or UndertowServletWebServerFactory bean yourself.

### 28. Security
