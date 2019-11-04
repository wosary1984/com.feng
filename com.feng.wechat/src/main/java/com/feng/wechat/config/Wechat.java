package com.feng.wechat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Wechat {
    @Value("${wechat.appid}")
    public String Appid;

    @Value("${wechat.appsecret}")
    public String Appsecret;

    @Value("${wechat.in_token}")
    public String in_token;
}