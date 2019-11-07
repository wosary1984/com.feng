package com.feng.wechat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.feng.util.SHA1;
import com.feng.wechat.config.Wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class WechatAccess {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private Wechat wechat;

    @Autowired
    HttpServletRequest request;

    @GetMapping("in")
    public String WechatIn(@RequestParam(name = "signature") String signature,
            @RequestParam(name = "timestamp") String timestamp, @RequestParam(name = "nonce") String nonce,
            @RequestParam(name = "echostr") String echostr) throws Exception {
        logger.info("wechat in:{},signature:{},timestamp:{},nonce:{},echostr:{}", request.getRequestURI(), signature,
                timestamp, nonce, echostr);
        if (checkSignature(signature, wechat.in_token, timestamp, nonce)) {
            return echostr;
        } else
            return null;
    }

    @RequestMapping(path = "in", method = RequestMethod.POST)
    public void WechatMessage(@RequestBody String data) throws Exception {
        logger.info("data:{}", data);

    }

    private boolean checkSignature(String signature, String token, String timestamp, String nonce) {

        List<String> p = new ArrayList<>();
        p.add(token);
        p.add(timestamp);
        p.add(nonce);
        p = p.stream().sorted().collect(Collectors.toList());

        String str = "";
        for (String v : p) {
            logger.info("{}", v);
            str = str.concat(v);
        }

        logger.info("encode str:{}", str);
        if (SHA1.sha1Encode2(str).equals(signature))
            return true;
        else
            return false;
    }

}