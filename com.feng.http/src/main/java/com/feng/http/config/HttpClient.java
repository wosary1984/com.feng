package com.feng.http.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClient {
    @Value("${http.maxTotal}")
    private Integer maxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.staleConnectionCheckEnabled}")
    private boolean staleConnectionCheckEnabled;

    /**
     * create connection pool manager, set max connection and max concurrency
     * 
     * @return pool manager
     */
    private PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManagerBean() {
        return this.getHttpClientConnectionManager();
    }

    /**
     * Instance connection pool
     * 
     * @param httpClientConnectionManager
     * @return
     */
    private HttpClientBuilder getHttpClientBuilder(PoolingHttpClientConnectionManager httpClientConnectionManager) {

        // HttpClientBuilder中的构造方法被protected修饰，
        // 所以这里不能直接使用new来实例化一个HttpClientBuilder，
        // 可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilderBean(
            @Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {
        return this.getHttpClientBuilder(httpClientConnectionManager);
    }

    private CloseableHttpClient getCloseableHttpClient(HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    public CloseableHttpClient getCloseableHttpClient() {
        PoolingHttpClientConnectionManager manager = this.getHttpClientConnectionManager();
        HttpClientBuilder builder = this.getHttpClientBuilder(manager);
        CloseableHttpClient client = this.getCloseableHttpClient(builder);
        return client;
    }

    @Bean
    public CloseableHttpClient getCloseableHttpClientBean() {
        return this.getCloseableHttpClient();
    }
    // @Bean
    // public CloseableHttpClient getCloseableHttpClientBean(
    // @Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
    // return this.getCloseableHttpClient(httpClientBuilder);
    // }

    /**
     * Builder是RequestConfig的一个内部类 通过RequestConfig的custom方法来获取到一个Builder对象
     * 设置builder的连接信息 这里还可以设置proxy，cookieSpec等属性。有需要的话可以在此设置
     * 
     * @return
     */
    private RequestConfig.Builder getRequestConfigBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout);
        // .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }

    @Bean(name = "builder")
    public RequestConfig.Builder getBuilderBean() {
        return this.getRequestConfigBuilder();
    }

    /**
     * use builder to create a request config object
     * 
     * @param builder
     * @return
     */
    public RequestConfig getRequestConfig(RequestConfig.Builder builder) {
        return builder.build();
    }
    @Bean
    public RequestConfig getRequestConfig() {
        RequestConfig.Builder builder = this.getRequestConfigBuilder();
        return this.getRequestConfig(builder);
    }
    // @Bean
    // public RequestConfig getRequestConfigBean(@Qualifier("builder") RequestConfig.Builder builder) {
    //     return this.getRequestConfig(builder);
    // }

}