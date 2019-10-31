package com.feng.wechat.service.wechat.model;

public enum WechatCustomMessageType {
    text("text"), image("image"), voice("voice"), news("news"), mpnews("mpnews"), msgmenu("msgmenu");

    private final String type;

    private WechatCustomMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}