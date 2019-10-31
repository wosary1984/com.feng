package com.feng.wechat.service.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WechatMediaSum extends WechatResponse {
    int total_count;
    int item_count;
    List<WechatMedia> medias;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public List<WechatMedia> getMedias() {
        return medias;
    }

    public void setMedias(List<WechatMedia> medias) {
        this.medias = medias;
    }

    public WechatMediaSum(JSONObject object) {
        super(object);
        this.setTotal_count(object.isNull("total_count") ? 0 : object.getInt("total_count"));
        this.setItem_count(object.isNull("item_count") ? 0 : object.getInt("item_count"));
        JSONArray array = (object != null && !object.isNull("item")) ? object.getJSONArray("item") : null;
        if (array != null) {
            if (this.getMedias() == null) {
                this.setMedias(new ArrayList<WechatMedia>());
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                this.getMedias().add(new WechatMedia(item));
            }
        }
    }

    public WechatMediaSum() {
    }
}