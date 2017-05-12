package com.hades.spring_jdbc.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hades.spring_jdbc.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Resource
    private TestService testService;

    @RequestMapping("/test0")
    public void test() {
        testService.test();
    }
}
