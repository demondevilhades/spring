package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 
 */
@Slf4j
@Component
public class Config {

    /**
     * 
     * @throws UnknownHostException
     */
    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        log.info("HostName = {}", localHost.getHostName());
        log.info("CanonicalHostName = {}", localHost.getCanonicalHostName());
        log.info("HostAddress = {}", localHost.getHostAddress());
    }
}
