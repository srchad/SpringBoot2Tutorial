package kakao.springboot.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//Cors Global 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/users/**").allowedOrigins("*");
    }
}
