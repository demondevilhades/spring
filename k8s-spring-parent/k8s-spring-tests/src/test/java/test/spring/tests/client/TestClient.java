package test.spring.tests.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author awesome
 */
@Component
@FeignClient(url = "http://localhost:18002/", name = "testClient")
public interface TestClient {

    /**
     * 
     * @return
     */
    @GetMapping("/testConfig")
    public String test();
}
