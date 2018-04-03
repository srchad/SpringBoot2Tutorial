package com.tistory.lky1001.boot2tutorial;

import com.tistory.lky1001.boot2tutorial.listener.ApplicationEnvironmentPreparedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationFailedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationPreparedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationReadyListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationStartedEventListener;
import com.tistory.lky1001.boot2tutorial.listener.ApplicationStartingEventListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

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

	@Component
	static class Test implements InitializingBean {
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
			System.out.println(uuid);
			System.out.println(secret);
			System.out.println(number);
			System.out.println(bignumber);
			System.out.println(ten);
			System.out.println(range);
		}
	}
}
