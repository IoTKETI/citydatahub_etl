package com.encore.smartcity.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SwaggerConfig {


    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {

        Collection<VendorExtension> vendorExtensions = new ArrayList<>();
        Contact contact = new Contact("CityHub", "http://cityhub-smartcity.com", "mail@cityhub.com");
        return new ApiInfo(

                "City Hub",
                "Apache NiFi Monitoring API",
                "v1.0.0",
                "String termsOfServiceUrl",
                contact,
                "CBNU © 2020 Created by CBNU-Big Data Lab & Blockchain",
                "String licenseUrl",
                vendorExtensions
        );
    }

}
