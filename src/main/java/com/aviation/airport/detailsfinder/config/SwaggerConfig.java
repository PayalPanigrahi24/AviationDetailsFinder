package com.aviation.airport.detailsfinder.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aviation.airport.detailsfinder.controller"))
                .paths(regex("/airport.*"))
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Airport and Runway Search ApI",
                "This APi works over 3 CSV file , which provides Country details along with Related Airports and Runways",
                "Version -1.0",
                "Terms of service",
                new Contact("Payal Panigrahi", "/linkedin/payal.panigrahi", "payal.panigrahi92@gmail.com"),
                "Aviation Service Version 1.0",
                "https://www.detailsfinder.org/licenses/LICENSE-1.0");
        return apiInfo;
    }


}
