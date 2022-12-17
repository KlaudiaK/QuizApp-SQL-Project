package pl.poznan.put.quizzy.swaggerConfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration{
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .apiInfo(getApiInfo())
    }

    private fun getApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("QUIZZY - REST API")
            .description("Quizzy rest api documentation")
            .version("1.0.0")
            .build()
    }

}