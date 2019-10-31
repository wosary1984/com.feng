package com.feng.wechat.service.wechat.model;

import java.util.List;

import org.json.JSONObject;

public class WechatUserProfile extends WechatResponse {
    int subscribe;
    String openid;
    String nickname;
    int sex;
    String language;
    String city;
    String province;
    String country;
    String headimgurl;
    long subscribeTime;
    String remark;
    int groupid;
    List<String> tagids;
    String subscribeScene;
    int qrScene;
    String qrSceneStr;

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public List<String> getTagids() {
        return tagids;
    }

    public void setTagids(List<String> tagids) {
        this.tagids = tagids;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public int getQrScene() {
        return qrScene;
    }

    public void setQrScene(int qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public WechatUserProfile() {
    }

    public WechatUserProfile(JSONObject object) {
        super(object);
        this.setSubscribe(object.isNull("subscribe") ? 0 : object.getInt("subscribe"));
        this.setOpenid(object.isNull("openid") ? null : object.getString("openid"));
        this.setNickname(object.isNull("nickname") ? null : object.getString("nickname"));
        this.setSex(object.isNull("sex") ? 0 : object.getInt("sex"));
        this.setLanguage(object.isNull("language") ? null : object.getString("language"));
        this.setCity(object.isNull("city") ? null : object.getString("city"));
        this.setProvince(object.isNull("province") ? null : object.getString("province"));
        this.setCountry(object.isNull("country") ? null : object.getString("country"));
        this.setHeadimgurl(object.isNull("headimgurl") ? null : object.getString("headimgurl"));
        this.setSubscribeTime(object.isNull("subscribe_time") ? 0 : object.getLong("subscribe_time"));
        this.setRemark(object.isNull("remark") ? null : object.getString("remark"));
        this.setGroupid(object.isNull("groupid") ? 0 : object.getInt("groupid"));
        this.setSubscribeScene(object.isNull("subscribe_scene") ? null : object.getString("subscribe_scene"));
        this.setQrScene(object.isNull("qr_scene") ? 0 : object.getInt("qr_scene"));
        this.setQrSceneStr(object.isNull("qr_scene_str") ? null : object.getString("qr_scene_str"));
    }
}