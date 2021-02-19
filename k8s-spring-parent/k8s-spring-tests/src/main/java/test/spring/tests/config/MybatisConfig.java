package test.spring.tests.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author awesome
 */
@Configuration
@MapperScan(basePackages = { "test.spring.tests.dao.**" })
public class MybatisConfig {
}
