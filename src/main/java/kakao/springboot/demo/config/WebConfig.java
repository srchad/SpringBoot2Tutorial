package kakao.springboot.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc // 이걸 적으면 안됩니다. 그럼 Spring boot가 제공해주는 기본 세팅을 쓰지 못한다.
@Configuration
public class WebConfig implements WebMvcConfigurer {

}
