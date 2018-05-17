package hades.webtask.service;

import hades.webtask.bean.WebTaskConfig;
import hades.webtask.util.HttpUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WebTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile boolean alive = true;

    @Value("${webtask.httpProxyHost}")
    private String httpProxyHost;
    @Value("${webtask.httpProxyPort}")
    private String httpProxyPort;
    @Value("${webtask.httpsProxyHost}")
    private String httpsProxyHost;
    @Value("${webtask.httpsProxyHost}")
    private String httpsProxyPort;

    @Resource
    private WebTaskConfig webTaskConfig;

    @ConfigurationProperties(prefix = "webtask.task0.config")
    @Bean
    public WebTaskConfig getWebTaskConfig() {
        return new WebTaskConfig();
    }

    @PostConstruct
    public void init() {
        if (StringUtils.isNotEmpty(httpProxyHost) && StringUtils.isNotEmpty(httpProxyPort)) {
            System.getProperties().setProperty("http.proxyHost", httpProxyHost);
            System.getProperties().setProperty("http.proxyPort", httpProxyPort);
            System.getProperties().setProperty("proxySet", "true");
        }
        if (StringUtils.isNotEmpty(httpsProxyHost) && StringUtils.isNotEmpty(httpsProxyPort)) {
            System.getProperties().setProperty("https.proxyHost", httpsProxyHost);
            System.getProperties().setProperty("https.proxyPort", httpsProxyPort);
            System.getProperties().setProperty("proxySet", "true");
        }
    }

    @Scheduled(cron = "${webtask.task0.cron_exp}")
    public void runTask() {
        logger.info("runTask:" + alive);
        if (alive) {
            try {
                HttpUtils.httpRequest(webTaskConfig);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public String startupTask() {
        alive = true;
        return String.valueOf(alive);
    }

    public String shutdownTask() {
        alive = false;
        return String.valueOf(alive);
    }

    public String taskStatus() {
        return new StringBuilder().append("alive=").append(alive).toString();
    }
}
