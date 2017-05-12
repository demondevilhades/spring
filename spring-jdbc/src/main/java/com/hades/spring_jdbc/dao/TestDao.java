package com.hades.spring_jdbc.dao;

import org.springframework.stereotype.Repository;

@Repository
public class TestDao extends Dao {

    public void test() {
        System.out.println(getJdbcTemplate().queryForList("select id from t_ptl_menu").size());
    }
}
