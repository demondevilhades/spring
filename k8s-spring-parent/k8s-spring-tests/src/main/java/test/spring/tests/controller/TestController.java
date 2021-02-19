package test.spring.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import test.spring.tests.client.TestClient;
import test.spring.tests.service.TestService;

/**
 * 
 * @author awesome
 */
@Slf4j
@RestController
@RequestMapping("/")
@Api(tags = "test")
public class TestController {

    @Autowired
    private TestClient testClient;

    @Autowired
    private TestService testService;
    
    @Value("${spring.redis.host}")
    public String testConfig;
    
    @Value("${singleServerConfig.address}")
    public String testConfig2;

    @ApiOperation("testFeign")
    @GetMapping("/testFeign")
    public String testFeign() {
        log.info("testFeign");
        return testClient.test();
    }

    @ApiOperation("testMysql")
    @GetMapping("/testMysql")
    public String testMysql() {
        log.info("testMysql");
        return testService.testDao();
    }

    @ApiOperation("testConfig")
    @GetMapping("/testConfig")
    public String testConfig() {
        log.info("testConfig");
        return testConfig;
    }

    @ApiOperation("testConfig2")
    @GetMapping("/testConfig2")
    public String testConfig2() {
        log.info("testConfig2");
        return testConfig2;
    }
}
