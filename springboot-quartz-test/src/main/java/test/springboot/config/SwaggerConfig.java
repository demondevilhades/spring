package test.springboot.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements CommandLineRunner {
    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public Docket api() {
        List<Parameter> pars = new ArrayList<Parameter>();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiOfflineInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("test.springboot")).paths(PathSelectors.any()).build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiOfflineInfo() {
        return new ApiInfoBuilder().title("test Api").description("test").version("1.0.0").build();
    }

    @Override
    public void run(String... args) throws Exception {
        Properties props = System.getProperties();
        String property = props.getProperty("os.name");

        if (property.startsWith("Win")) {
            String swaggerUrl = "http://127.0.0.1:" + serverPort + contextPath + "/swagger-ui.html";
            try {
                Runtime.getRuntime().exec("cmd /c start " + swaggerUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
