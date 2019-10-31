package com.feng.wechat.service.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WechatNews extends WechatResponse {
    long create_time;
    long update_time;
    List<WechatNewsItem> items;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public List<WechatNewsItem> getItems() {
        return items;
    }

    public void setItems(List<WechatNewsItem> items) {
        this.items = items;
    }

    public void addItem(WechatNewsItem item) {
        if (this.getItems() == null) {
            this.setItems(new ArrayList<WechatNewsItem>());
        }
        this.getItems().add(item);
    }

    public WechatNews(JSONObject object) {
        super(object);
        JSONArray array = object.isNull("news_item") ? null : object.getJSONArray("news_item");

        if (array != null) {
            if (this.getItems() == null) {
                this.setItems(new ArrayList<WechatNewsItem>());
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                this.getItems().add(new WechatNewsItem(o));
            }
        }
    }

    public JSONObject toJson() {
        JSONObject article = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < this.getItems().size(); i++) {
            JSONObject item = new JSONObject(this.getItems().get(i).toJson());
            array.put(item);
        }
        article.put("articles", array);
        return article;
    }

    public WechatNews() {
    }
}