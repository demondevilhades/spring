package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author 
 */
@SpringBootApplication
public class App {

    /**
     * 
     * @param args
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.setProperty("net.hostName", localHost.getHostName());
        System.setProperty("net.canonicalHostName", localHost.getCanonicalHostName());
        System.setProperty("net.hostAddress", localHost.getHostAddress());
        SpringApplication.run(App.class, args);
    }
}
