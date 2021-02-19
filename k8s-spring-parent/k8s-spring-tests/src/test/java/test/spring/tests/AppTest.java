package test.spring.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author awesome
 */
@SpringBootApplication(exclude = { org.springframework.cloud.kubernetes.client.KubernetesClientAutoConfiguration.class,
        org.springframework.cloud.kubernetes.client.discovery.KubernetesDiscoveryClientAutoConfiguration.class,
        org.springframework.cloud.kubernetes.client.loadbalancer.KubernetesClientLoadBalancerAutoConfiguration.class })
@EnableTransactionManagement
public class AppTest {
    
    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }
}
