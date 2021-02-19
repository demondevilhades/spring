package test.spring.tests.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 
 * @author awesome
 */
@EnableOpenApi
@Configuration
public class SwaggerConfig implements CommandLineRunner {
    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).servers(null).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("tests").description("tests swagger").version("V1.0").build();
    }

    @Override
    public void run(String... args) throws Exception {
        Properties props = System.getProperties();
        String property = props.getProperty("os.name");

        if (property.startsWith("Win")) {
            String swaggerUrl = "http://127.0.0.1:" + serverPort + contextPath + "/swagger-ui/";
            try {
                Runtime.getRuntime().exec("cmd /c start " + swaggerUrl);
            } catch (Exception e) {
            }
        }
    }
}
