package com.xsquare.common.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zaizhongyang
 * @description
 * @date 2017/3/5
 */
@Component
public class HttpClientUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
    //最大连接数
    private static int maxTotal = 100;
    //并发数
    private static int defaultMaxPerRoute = 50;
    //创建连接的最长时间
    private static int connectTimeout = 3000;
    //从连接池中获取到连接的最长时间
    private static int connectionRequestTimeout = 3000;
    //数据传输的最长时间
    private static int socketTimeout = 100000;
    //连接空闲，单位：秒
    private static int connectionIdleTimeout = 30;
    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient httpsClient;
    private static IdleConnectionMonitorThread connectionMonitorThread;

    static {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        //并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout)
                .setExpectContinueEnabled(true).build();
        //HttpBuilder httpBuilder=HttpClients.custom();httpBuilder构造合适的http方法
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(httpClientConnectionManager).build();
        httpsClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).setConnectionManager(httpClientConnectionManager).build();
        // 单独线程做空闲连接监控和处理
        connectionMonitorThread = new IdleConnectionMonitorThread(httpClientConnectionManager,
                connectionIdleTimeout);
        connectionMonitorThread.start();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(apiUrl);
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            LOG.error("http get请求失败！", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error("http post关闭流失败！", e);
                }
            }
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            LOG.error("http post请求失败！", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error("http post关闭流失败！", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * @param apiUrl
     * @param json json对象
     * @param headerMap 请求头
     * @return
     */
    public static String doPost(String apiUrl, String json, Map<String, String> headerMap) {
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }
        try {
            StringEntity stringEntity = new StringEntity(json, "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            LOG.error("http post json请求失败！请求url为：{}，请求json为：{}", apiUrl, json, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error("http post json关闭流失败！", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpsClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            LOG.error("https post请求失败！", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error("https post 关闭流失败！", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * @param apiUrl API接口URL
     * @param json JSON对象
     * @param headerMap 请求头
     * @return
     */
    public static String doPostSSL(String apiUrl, Object json, Map<String, String> headerMap) {

        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;
        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }
        try {
            //解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpsClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            LOG.error("https post json请求失败！", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error("http post json关闭流失败！", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory factory = null;
        try {
            SSLContext sslcontext = SSLContexts.custom().useSSL().build();
            sslcontext.init(null, new X509TrustManager[] { new HttpsTrustManager() }, new SecureRandom());
            factory = new SSLConnectionSocketFactory(sslcontext,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        } catch (GeneralSecurityException e) {
            LOG.error("创建SSL安全连接失败！", e);
        }
        return factory;
    }

    /**
     * Shutdown the httpUtil
     *
     * @throws IOException IOException
     */
    public static void shutdown() throws IOException {
        connectionMonitorThread.shutdown();
        httpClient.close();
    }

    /**
     * A Connection monitor thread which close the idle connections in HttpClientConnectionManager
     */
    public static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connectionManager;
        private final int connectionIdleTimeout;
        private volatile boolean shutdown;

        IdleConnectionMonitorThread(HttpClientConnectionManager connectionManager, int connectionIdleTimeout) {
            super();
            this.connectionManager = connectionManager;
            this.connectionIdleTimeout = connectionIdleTimeout;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // close expired connections
                        connectionManager.closeExpiredConnections();
                        // close connections that have been idle longer than specific sec
                        connectionManager.closeIdleConnections(connectionIdleTimeout, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException e) {
                LOG.error("httpclient连接池空闲连接管理出现异常。");
            }
        }

        void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
