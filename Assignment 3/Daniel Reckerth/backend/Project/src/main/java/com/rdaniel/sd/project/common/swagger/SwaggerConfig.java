package com.rdaniel.sd.project.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/***
 * Swagger configuration.
 * It can be accessed withing http://my-domain/swagger-ui
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apiDocket() {
    List<Response> responseMessages = List.of(
        new ResponseBuilder().code("500").description("Unexpected error, the server could not meet your request.").build()
    );

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.rdaniel.sd.project"))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
        .globalResponses(HttpMethod.GET, responseMessages)
        .globalResponses(HttpMethod.DELETE, responseMessages)
        .globalResponses(HttpMethod.POST, responseMessages)
        .globalResponses(HttpMethod.PUT, responseMessages)
        .globalResponses(HttpMethod.PATCH, responseMessages)
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
        .title("SD - Help Desk Project")
        .version("1.0.0")
        .build();
  }
}
