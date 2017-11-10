package com.hades.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hades.springcloud.service.TestService;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * http://localhost:8781/customerTest?param=test0
     * 
     * @param param
     * @return
     */
    @RequestMapping(value = "/customerTest")
    public String customerTest(@RequestParam String param) {
        return testService.testService(param);
    }
}
