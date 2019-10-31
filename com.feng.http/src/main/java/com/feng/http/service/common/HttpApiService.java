package com.feng.http.service.common;

import java.io.File;
import java.util.Map;

public interface HttpApiService {

    /**
     * get request without url parameters， if response code is 200 return body, if
     * response is not 200, return null
     * 
     * @param url
     * @param header
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url, Map<String, String> header) throws Exception;

    /**
     * Get request with url parameters if response code is 200 return body, if
     * response is not 200, return null
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url, Map<String, String> urlParams, Map<String, String> header) throws Exception;

    /**
     * post data as application/x-www-form-urlencoded
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public HttpResult doFormPost(String url, Map<String, Object> from, Map<String, String> header) throws Exception;

    /**
     * post application/json data with out url params
     * 
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public HttpResult doJsonPost(String url, String data) throws Exception;

    /**
     * post application/json data with url params
     * 
     * @param url
     * @param data
     * @param urlParams
     * @return
     * @throws Exception
     */
    public HttpResult doJsonPost(String url, String data, Map<String, String> urlParams) throws Exception;

    /**
     * post media without url params
     * 
     * @param url
     * @param file
     * @param fileName
     * @return
     */
    public HttpResult doMediaPost(String url, File file, String id) throws Exception;

    /**
     * post media with url params
     * 
     * @param url
     * @param file
     * @param id
     * @param urlParams
     * @return
     * @throws Exception
     */
    public HttpResult doMediaPost(String url, File file, String id, Map<String, String> urlParams) throws Exception;
}