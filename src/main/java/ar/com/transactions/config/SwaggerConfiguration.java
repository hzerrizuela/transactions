package ar.com.transactions.config;

import ar.com.transactions.dto.TransactionDTO;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket produceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                /**
                 * los modelos polimorficos de siniestros no me los toma la libreria a menos que los agregue a mano
                 */
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.com.transactions.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    //// Describe your apis
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Transactions API")
                .description("Documentacion de la API Autos.")
                .build();
    }

    private Predicate<String> paths() {
        // Match all paths except /error
        return Predicates.and(PathSelectors.regex("/transactions.*"));
    }
}