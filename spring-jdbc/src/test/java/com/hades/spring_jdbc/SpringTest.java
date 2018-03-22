package com.hades.spring_jdbc;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/applicationContext.xml", "classpath:spring/dataSource.xml",
        "classpath:spring/dispatcher-servlet.xml" })
public class SpringTest {

}
