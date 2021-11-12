package com.example.demo.PR4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.InputStream;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("localhost:80")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.PR4"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(
                        new Tag("User", "Взаимодействие с Пользователями"),
                        new Tag("Truck", "Взаимодействие с автомобилями")
                )
                .ignoredParameterTypes(Resource.class, InputStream.class);


    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Документация по API приложения")
                .description("Здесь вся информация, необходимая для понимания работы данного REST-API приложения")
                .version("V0.0.0")
                .build();
    }
}
