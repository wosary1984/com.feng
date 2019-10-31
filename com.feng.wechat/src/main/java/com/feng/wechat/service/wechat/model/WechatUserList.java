package com.feng.wechat.service.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WechatUserList extends WechatResponse {

    int total;
    int count;
    List<String> openids;
    String nextOpenid;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNextOpenid() {
        return nextOpenid;
    }

    public void setNextOpenid(String nextOpenid) {
        this.nextOpenid = nextOpenid;
    }

    public List<String> getOpenids() {
        return openids;
    }

    public void setOpenids(List<String> openids) {
        this.openids = openids;
    }

    public void appendValue(JSONObject object) {
        this.setTotal(object.isNull("total") ? 0 : object.getInt("total"));
        this.setCount(object.isNull("count") ? 0 : object.getInt("count"));
        this.setNextOpenid(object.isNull("next_openid") ? null : object.getString("next_openid"));

        JSONObject data = object.isNull("data") ? null : object.getJSONObject("data");
        JSONArray array = (data != null && !data.isNull("openid")) ? data.getJSONArray("openid") : null;

        if (array != null) {
            if (this.getOpenids() == null) {
                this.setOpenids(new ArrayList<String>());
            }
            for (int i = 0; i < array.length(); i++) {
                this.getOpenids().add((String) array.get(i));
            }
        }
    }

    public WechatUserList() {
    }

}