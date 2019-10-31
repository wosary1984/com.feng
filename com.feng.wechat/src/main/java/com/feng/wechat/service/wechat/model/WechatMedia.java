package com.feng.wechat.service.wechat.model;

import org.json.JSONObject;

public class WechatMedia extends WechatResponse {
    String type;
    String media_id;
    String url;
    String name;
    long update_time;
    long created_at;
    WechatNews news;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WechatMedia() {
    }

    public WechatMedia(JSONObject object) {
        super(object);
        this.setType(object.isNull("type") ? null : object.getString("type"));
        this.setMedia_id(object.isNull("media_id") ? null : object.getString("media_id"));
        this.setUrl(object.isNull("url") ? null : object.getString("url"));
        this.setName(object.isNull("name") ? null : object.getString("name"));
        this.setCreated_at(object.isNull("created_at") ? 0 : object.getLong("created_at"));
        this.setUpdate_time(object.isNull("update_time") ? 0 : object.getLong("update_time"));

        JSONObject content = object.isNull("content") ? null : object.getJSONObject("content");
        if (content != null) {
            this.setNews(new WechatNews(content));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public WechatNews getNews() {
        return news;
    }

    public void setNews(WechatNews news) {
        this.news = news;
    }
}