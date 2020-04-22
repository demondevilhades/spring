package test.springboot.job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestInvokeMethod {
    
    public void test(String arg) {
        log.info("TestInvokeMethod.test = " + arg);
    }
}
