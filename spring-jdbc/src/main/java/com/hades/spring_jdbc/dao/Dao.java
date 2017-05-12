package com.hades.spring_jdbc.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class Dao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
