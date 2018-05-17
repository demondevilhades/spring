package hades.webtask.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    public static void httpGet(String url, String userAgent, Map<String, String> cookieMap, boolean logEntity)
            throws IOException {
        HttpGet httpGet = new HttpGet(url);

        CookieStore cookieStore = new BasicCookieStore();
        if (cookieMap != null) {
            for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
                cookie.setDomain(httpGet.getURI().getHost());
                cookieStore.addCookie(cookie);
            }
        }

        HttpHost proxyHttpHost = getProxyHttpHost(httpGet);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                .setUserAgent(userAgent).setProxy(proxyHttpHost).build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());

            if (logEntity) {
                LOGGER.info("Entity=\r\n" + EntityUtils.toString(response.getEntity()));
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpGet.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    public static void httpPost(String url, String userAgent, Map<String, String> cookieMap, boolean logEntity)
            throws IOException {
        HttpPost httpPost = new HttpPost(url);

        CookieStore cookieStore = new BasicCookieStore();
        if (cookieMap != null) {
            for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
                cookie.setDomain(httpPost.getURI().getHost());
                cookieStore.addCookie(cookie);
            }
        }

        HttpHost proxyHttpHost = getProxyHttpHost(httpPost);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                .setUserAgent(userAgent).setProxy(proxyHttpHost).build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());

            if (logEntity) {
                LOGGER.info("Entity=\r\n" + EntityUtils.toString(response.getEntity()));
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    public static String shutdownApp(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);

        CloseableHttpClient httpClient = HttpClients.createSystem();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpPost.releaseConnection();
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                throw e;
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static HttpHost getProxyHttpHost(HttpRequestBase httpRequestBase) {
        HttpHost proxyHttpHost = null;
        String proxySet = System.getProperties().getProperty("proxySet");
        if (proxySet != null && "true".equalsIgnoreCase(proxySet)) {
            String protocol = httpRequestBase.getProtocolVersion().getProtocol();
            if ("HTTP".equals(protocol)) {
                proxyHttpHost = new HttpHost(System.getProperties().getProperty("http.proxyHost"),
                        Integer.parseInt(System.getProperties().getProperty("http.proxyPort")));
            } else if ("HTTPS".equals(protocol)) {
                proxyHttpHost = new HttpHost(System.getProperties().getProperty("https.proxyHost"),
                        Integer.parseInt(System.getProperties().getProperty("https.proxyPort")));
            }
        }
        return proxyHttpHost;
    }
}
