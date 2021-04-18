package com.n26.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class TransactionsStatisticsConfiguration {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/*"))
                .apis(RequestHandlerSelectors.basePackage("com.n26.api"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Transactions Statistics API",
                "Realtime transactions statistics for last 60 seconds.",
                "1.0",
                "Free to Use",
                new Contact("Ashish Deshpande", "https://www.linkedin.com/in/deshpandead/", "ashishdeshpande123@gmail.com"),
                "API License",
                "https://www.linkedin.com/in/deshpandead/",
                Collections.EMPTY_LIST
        );
    }
}
