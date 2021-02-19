package test.spring.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.spring.tests.dao.TestDao;

/**
 * 
 * @author awesome
 */
@Service
public class TestService {

    @Autowired
    private TestDao testDao;
    
    /**
     * 
     * @return
     */
    public String testDao() {
        return testDao.version();
    }
}
