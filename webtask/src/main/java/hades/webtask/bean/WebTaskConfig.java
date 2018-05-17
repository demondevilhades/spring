package hades.webtask.bean;

import java.util.Map;

public class WebTaskConfig {

    private String url;
    private String userAgent;
    private Map<String, String> cookie;
    private boolean logEntity;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public boolean isLogEntity() {
        return logEntity;
    }

    public void setLogEntity(boolean logEntity) {
        this.logEntity = logEntity;
    }
}
