package com.tistory.lky1001.boot2tutorial;

import com.tistory.lky1001.boot2tutorial.handlers.UserHandler;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationEnvironmentPreparedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationFailedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationPreparedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationReadyListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationStartedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationStartingEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class Boot2tutorialApplication {

	public static void main(String[] args) {
		// application ready event는 listener에 등록하지 않고 bean만 생성해도 됨.

		/*
			onApplicationEvent : ApplicationStartingEvent
			onApplicationEvent : ApplicationEnvironmentPreparedEvent
			onApplicationEvent : ApplicationPreparedEvent
			onApplicationEvent : ApplicationStartedEvent
			onApplicationEvent : ApplicationReadyEvent
		/**/

		new SpringApplicationBuilder(Boot2tutorialApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.listeners(new ApplicationEnvironmentPreparedEventListener(),
						new ApplicationFailedEventListener(),
						new ApplicationPreparedEventListener(),
						new ApplicationReadyListener(),
						new ApplicationStartedEventListener(),
						new ApplicationStartingEventListener())
				.run(args);
	}

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(@Autowired UserHandler userHandler) {
        return route(GET("/mono/{user}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/mono/{user}/customers").and(accept(APPLICATION_JSON)), userHandler::getUserCustomers)
                .andRoute(DELETE("/mono/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser)
				.andRoute(GET("/mono/{user}/details").and(accept(APPLICATION_JSON)), userHandler::getUserDetails)
				.andRoute(GET("/mono/{user}/test").and(accept(APPLICATION_JSON)), userHandler::getUserDetailTest);
	}

	@Component
	static class Test implements InitializingBean {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		@Value("${my.uuid}")
		public String uuid;

		@Value("${my.secret}")
		public String secret;

		@Value("${my.num}")
		public int number;

		@Value("${my.bignumber}")
		public long bignumber;

		@Value("${my.number.less.than.ten}")
		public int ten;

		@Value("${my.number.in.range}")
		public int range;

		@Override
		public void afterPropertiesSet() throws Exception {
			/*
			4810cef7-da2c-4c54-bfa2-1eaaad8d0596
			496a16fbf42b8b8d613fd39a9cd5d4bc
			2007443276
			473659731777239348
			2
			3097
			/**/
			logger.debug("uuid : {}", uuid);
			logger.debug("secret : {}", secret);
			logger.debug("uuid : {}", number);
			logger.debug("bignumber : {}", bignumber);
			logger.debug("ten : {}", ten);
			logger.debug("range : {}", range);
		}
	}
}
