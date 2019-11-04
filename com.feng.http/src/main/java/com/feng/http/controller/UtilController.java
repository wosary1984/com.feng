package com.feng.http.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.feng.http.service.common.HttpApiService;
import com.feng.util.SHA1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/util")
public class UtilController extends BaseController {

    @Resource
    private HttpApiService http;

    @RequestMapping("sha1")
    public List<String> sha1(@RequestParam(name = "value") String value) throws Exception {
        List<String> r = new ArrayList<String>();
        r.add(SHA1.sha1Encode1(value));
        r.add(SHA1.sha1Encode2(value));

        return r;
    }
}