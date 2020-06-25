package com.bykj.utils;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 11:38 2019/5/22
 */
@EnableSwagger2
public class Swagger2Config {

    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bykj.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("hanchuang")
                .description("description")
                .termsOfServiceUrl("http://localhost:8088/bykj/swagger-ui.html")
                .license("bykj")
                .version("v1.0")
                .build();
    }
}
