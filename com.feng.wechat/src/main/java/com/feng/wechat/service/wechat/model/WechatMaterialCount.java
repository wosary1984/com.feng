package com.feng.wechat.service.wechat.model;

import org.json.JSONObject;

public class WechatMaterialCount extends WechatResponse {
    int voice_count;
    int video_count;
    int image_count;
    int news_count;

    public int getVoice_count() {
        return voice_count;
    }

    public void setVoice_count(int voice_count) {
        this.voice_count = voice_count;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public int getImage_count() {
        return image_count;
    }

    public void setImage_count(int image_count) {
        this.image_count = image_count;
    }

    public int getNews_count() {
        return news_count;
    }

    public void setNews_count(int news_count) {
        this.news_count = news_count;
    }

    public WechatMaterialCount() {
    }

    public WechatMaterialCount(JSONObject object) {
        super(object);
        this.setVoice_count(object.isNull("voice_count") ? 0 : object.getInt("voice_count"));
        this.setVideo_count(object.isNull("video_count") ? 0 : object.getInt("video_count"));
        this.setImage_count(object.isNull("image_count") ? 0 : object.getInt("image_count"));
        this.setNews_count(object.isNull("news_count") ? 0 : object.getInt("news_count"));
    }
}