package com.hades.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class App {

    /**
     * http://localhost:8771/test?param=test0
     * 
     * @param param
     * @return
     */
    @RequestMapping("/test")
    public String test(@RequestParam String param) {
        return "test:" + param;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
