package test.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 
 * @author awesome
 */
@SpringBootApplication(exclude = { org.springframework.cloud.kubernetes.client.KubernetesClientAutoConfiguration.class,
        org.springframework.cloud.kubernetes.client.discovery.KubernetesDiscoveryClientAutoConfiguration.class,
        org.springframework.cloud.kubernetes.client.loadbalancer.KubernetesClientLoadBalancerAutoConfiguration.class })
@EnableConfigServer
public class AppTest {
    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }
}
