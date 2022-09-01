package com.encore.smartcity.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class ResourceHandlerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/swagger/**")
                .addResourceLocations("classpath:/static/swagger-v2.1.3/dist/");

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui").setViewName("swagger/swagger-index");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }
}
