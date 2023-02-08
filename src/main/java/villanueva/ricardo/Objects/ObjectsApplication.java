package villanueva.ricardo.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import villanueva.ricardo.Objects.Interceptors.MyAuthInterceptor;

@SpringBootApplication
public class ObjectsApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ObjectsApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyAuthInterceptor()).addPathPatterns("/objects/**").addPathPatterns("/settings").addPathPatterns("/download/**").addPathPatterns("/deleteBucket/**");

	}
}
