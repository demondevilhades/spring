package hades.webtask.bean;

import java.util.Map;

public class WebTaskConfig {

    private String url;
    private String requestMethod;
    private String userAgent;
    private Map<String, String> cookie;
    private Map<String, String> headers;
    private Map<String, String> params;
    private String paramCharset;
    private boolean logEntity;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getParamCharset() {
        return paramCharset;
    }

    public void setParamCharset(String paramCharset) {
        this.paramCharset = paramCharset;
    }

    public boolean isLogEntity() {
        return logEntity;
    }

    public void setLogEntity(boolean logEntity) {
        this.logEntity = logEntity;
    }
}
