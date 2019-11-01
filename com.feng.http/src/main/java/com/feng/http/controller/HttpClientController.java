package com.feng.http.controller;

import javax.annotation.Resource;

import com.feng.http.service.common.HttpApiService;
import com.feng.http.service.common.HttpResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/httpclient")
public class HttpClientController extends BaseController {

    @Resource
    private HttpApiService http;

    @RequestMapping("/test")
    public HttpResult test() throws Exception {
        HttpResult r = http.doGet("http://www.baidu.com", null);
        return r;
    }
}