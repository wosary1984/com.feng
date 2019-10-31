package com.feng.wechat.service.wechat.model;

import org.json.JSONObject;

public class WechatResponse {
    int errcode;
    String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public WechatResponse() {
    }

    public WechatResponse(JSONObject object) {
        this.setErrcode(object.isNull("errcode") ? 0 : object.getInt("errcode"));
        this.setErrmsg(object.isNull("errmsg") ? null : object.getString("errmsg"));
    }
}