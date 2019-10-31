package com.feng.http.service.common.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.feng.http.service.common.HttpApiService;
import com.feng.http.service.common.HttpResult;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author I068981
 */
@Service
public class HttpApiServiceImp implements HttpApiService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
     @Autowired
    private CloseableHttpClient httpClient;


    @Autowired
    private RequestConfig config;

    public HttpResult doGet(String url, Map<String, String> header) throws Exception {
        // declare http get request
        HttpGet httpGet = new HttpGet(url);
        // set get request header
        if (header != null) {
            header.forEach((k, v) -> {
                httpGet.setHeader(k, v);
            });
        }
        // load connection configuration
        httpGet.setConfig(config);
        // start request
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        // check response code
        HttpResult r = new HttpResult();
        r.setCode(response.getStatusLine().getStatusCode());
        r.setBody(EntityUtils.toString(response.getEntity(), "UTF-8"));
        if (response.getFirstHeader("x-csrf-token") != null) {
            r.setCsrfToken(response.getFirstHeader("x-csrf-token").getValue());
        }
        return r;
    }

    public HttpResult doGet(String url, Map<String, String> urlParams, Map<String, String> header) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (urlParams != null) {
            // concat url parameters
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return this.doGet(uriBuilder.build().toString(), header);
    }

    /**
     * post data as application/x-www-form-urlencoded
     * 
     * @param url  post url
     * @param from post form data
     * @param post request header
     */
    public HttpResult doFormPost(String url, Map<String, Object> from, Map<String, String> header) throws Exception {
        // declare httpPost request
        HttpPost httpPost = new HttpPost(url);
        // load connection confiuration
        httpPost.setConfig(config);

        // set post request header
        if (header != null) {
            header.forEach((k, v) -> {
                httpPost.setHeader(k, v);
            });
        }

        // pack requst body as form
        if (from != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : from.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
            logger.info("Executing request " + httpPost.getRequestLine());

        }
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(),
                EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    /**
     * post application/json data without url params
     * 
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public HttpResult doJsonPost(String url, String data) throws Exception {
        // declare httpPost request
        HttpPost httpPost = new HttpPost(url);
        // load connection confiuration
        httpPost.setConfig(config);
        httpPost.setHeader("Content-Type", "application/json");

        StringEntity se = new StringEntity(data, "UTF-8");
        httpPost.setEntity(se);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(),
                EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    public HttpResult doJsonPost(String url, String data, Map<String, String> urlParams) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (urlParams != null) {
            // concat url parameters
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return this.doJsonPost(uriBuilder.build().toString(), data);
    }

    @Override
    public HttpResult doMediaPost(String url, File file, String id) throws Exception {
        FileBody fileBody = new FileBody(file);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                //
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                // .addTextBody("params", "{....}")
                .addPart(id, fileBody);
        HttpEntity multiPartEntity = builder.build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(multiPartEntity);


        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(),
                EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    @Override
    public HttpResult doMediaPost(String url, File file, String id, Map<String, String> urlParams)
            throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (urlParams != null) {
            // concat url parameters
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return this.doMediaPost(uriBuilder.build().toString(), file, id);
    }
}