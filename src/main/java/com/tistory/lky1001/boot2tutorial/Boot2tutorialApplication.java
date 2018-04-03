package com.tistory.lky1001.boot2tutorial;

import com.tistory.lky1001.boot2tutorial.listener.ApplicationEnvironmentPreparedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationFailedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationPreparedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationReadyListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationStartedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationStartingEventListener;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

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
}
