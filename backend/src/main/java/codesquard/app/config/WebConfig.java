package codesquard.app.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import codesquard.app.authenticate_user.resolver.LoginUserArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedHeaders("*")
			.allowCredentials(true) // 쿠키 인증 요청 허용
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginUserArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
}
