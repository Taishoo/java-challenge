package jp.co.axa.apidemo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value("${app.client.credential.id}")
    private String clientId;

    @Value("${app.client.credential.secret}")
    private String clientSecret;

    @Bean
    public Docket swaggerConfiguration() {
        // Creates configuration for swagger
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("jp.co.axa"))
                .build()
                .apiInfo(metaData());
    }

    // Adds additional information and metadata to Swagger documentation
    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("AXA Java Challenge")
                .description(
                        "API Documentation for accessing and modifying the Employee controller<br><br>" +
                                "x-client-id=" + clientId + "<br>" +
                                "x-client-secret=" + clientSecret + "<br>"
                )
                .contact(new Contact("Albert V. De Leon Jr.", "https://github.com/Taishoo", "kitamoriken1@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

}
