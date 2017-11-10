package com.hades.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {

    @Autowired
    RestTemplate restTemplate;

    public String testService(String param) {
        return restTemplate.getForObject("http://spring-cloud-producer-demo/test?param=" + param, String.class);
    }
}
