package com.postgres.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableOpenApi
public class Swagger3Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .globalRequestParameters(buildGlobalHeaderParam());
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("Swagger 3.0")
                .description("Swagger 3.0配置Demo")
                .termsOfServiceUrl("https://blog.csdn.net/ylforever")
                .version("1.0")
                .build();
        return apiInfo;
    }

    /**
     * 构建公共参数
     *
     * @return elon
     */
    private List<RequestParameter> buildGlobalHeaderParam() {
        List<RequestParameter> globalParamList = new ArrayList<>();
        RequestParameter account = new RequestParameterBuilder().name("account")
                .in(ParameterType.HEADER)
                .example(new ExampleBuilder().value("zh1234").build())
                .build();
        globalParamList.add(account);

        RequestParameter name = new RequestParameterBuilder().name("name")
                .in(ParameterType.HEADER)
                .example(new ExampleBuilder().value("zhang san").build())
                .build();
        globalParamList.add(name);

        RequestParameter schema = new RequestParameterBuilder().name("schema")
                .in(ParameterType.HEADER)
                .example(new ExampleBuilder().value("schema001").build())
                .build();
        globalParamList.add(schema);

        return globalParamList;
    }
}
