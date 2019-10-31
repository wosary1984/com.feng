package com.feng.wechat.service.wechat.model;

import org.json.JSONObject;

public class WechatToken extends WechatResponse {
    String accessToken;
    long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public WechatToken(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public WechatToken() {
    }

    public WechatToken(JSONObject object) {
        super(object);
        this.setAccessToken(object.isNull("access_token") ? null : object.getString("access_token"));
        this.setExpiresIn(object.isNull("expires_in") ? 0 : object.getLong("expires_in"));
    }

}