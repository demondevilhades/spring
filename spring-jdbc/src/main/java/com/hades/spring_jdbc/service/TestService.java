package com.hades.spring_jdbc.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hades.spring_jdbc.dao.TestDao;

@Service
public class TestService {
    
    @Resource
    private TestDao testDao;

    public void test() {
        testDao.test();
    }
}
