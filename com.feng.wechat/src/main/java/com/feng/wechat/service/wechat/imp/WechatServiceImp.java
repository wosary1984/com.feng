package com.feng.wechat.service.wechat.imp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.feng.http.service.common.HttpApiService;
import com.feng.http.service.common.HttpResult;
import com.feng.wechat.config.Wechat;
import com.feng.wechat.service.wechat.WechatService;
import com.feng.wechat.service.wechat.model.WechatCustomMessageType;
import com.feng.wechat.service.wechat.model.WechatMaterialCount;
import com.feng.wechat.service.wechat.model.WechatMedia;
import com.feng.wechat.service.wechat.model.WechatMediaSum;
import com.feng.wechat.service.wechat.model.WechatNews;
import com.feng.wechat.service.wechat.model.WechatResponse;
import com.feng.wechat.service.wechat.model.WechatToken;
import com.feng.wechat.service.wechat.model.WechatUserList;
import com.feng.wechat.service.wechat.model.WechatUserProfile;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImp implements WechatService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ACCESS_TOKEN = null;
    private long ACCESS_TOKEN_TIME_MILLIS = 0;
    private long VALID_DIFFS = 7100;

    @Resource
    private HttpApiService http;

    @Resource
    private Wechat wechat;

    private boolean checkToken() {
        long now = System.currentTimeMillis();
        if ((now - ACCESS_TOKEN_TIME_MILLIS) / 1000 < VALID_DIFFS) {
            return true;
        } else {

            return false;
        }
    }

    public WechatToken getWechatAccessToken() throws Exception {

        if (checkToken()) {
            long now = System.currentTimeMillis();
            return new WechatToken(this.ACCESS_TOKEN, (now - this.ACCESS_TOKEN_TIME_MILLIS) / 1000);
        }
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("grant_type", "client_credential");
        parameters.put("appid", wechat.Appid);
        parameters.put("secret", wechat.Appsecret);

        HttpResult r = http.doGet(GET_ACCESS_TOKEN, parameters, null);
        WechatToken token = new WechatToken(new JSONObject(r.getBody()));
        this.ACCESS_TOKEN = token.getAccessToken();
        this.ACCESS_TOKEN_TIME_MILLIS = System.currentTimeMillis();

        return token;
    }

    public WechatResponse getUsers(WechatUserList users) throws Exception {

        WechatToken token = this.getWechatAccessToken();
        if (users == null) {
            users = new WechatUserList();
        }

        Map<String, String> urlParam = new HashMap<String, String>();
        urlParam.put("access_token", token.getAccessToken());
        if (users.getTotal() != users.getCount() && users.getNextOpenid() != null) {
            urlParam.put("next_openid", users.getNextOpenid());
        }

        HttpResult r = http.doGet(GET_USERS, urlParam, null);
        users.appendValue(new JSONObject(r.getBody()));

        if (users.getTotal() != users.getCount() && users.getNextOpenid() != null) {
            return this.getUsers(users);
        }
        return users;
    }

    public WechatResponse getUserProfie(String openid) throws Exception {

        WechatToken token = this.getWechatAccessToken();
        Map<String, String> header = new HashMap<String, String>();
        header.put("charset", CHARSET_UTF_8);

        Map<String, String> param = new HashMap<String, String>();
        param.put("access_token", token.getAccessToken());
        param.put("openid", openid);
        param.put("lang", "zh_CN");

        HttpResult r = http.doGet(GET_USER_PROFILE, param, header);

        WechatUserProfile profile = new WechatUserProfile(new JSONObject(r.getBody()));
        return profile;
    }

    public WechatResponse sendCustomMessage(String toUser, WechatCustomMessageType type, Object value)
            throws Exception {

        WechatToken token = this.getWechatAccessToken();

        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> header = new HashMap<String, String>();
        header.put("charset", CHARSET_UTF_8);

        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());

        JSONObject data = wrapCustomMessage(toUser, type.getType(), value);

        logger.info("sendCustomeMessage body: {}", data.toString());

        HttpResult r = http.doJsonPost(SEND_CUSTOMER_MESSAGE, data.toString(), urlParams);
        WechatResponse response = new WechatResponse(new JSONObject(r.getBody()));
        return response;
    }

    public WechatResponse sendTextCustomMessage(String toUser, Object value) throws Exception {

        return this.sendCustomMessage(toUser, WechatCustomMessageType.text, value);
    }

    private JSONObject wrapCustomMessage(String toUser, String type, Object value) {
        JSONObject data = new JSONObject();
        data.put("touser", toUser);
        data.put("msgtype", type);
        data.put(type, value);
        return data;
    }

    @Override
    public WechatResponse sendMassCustomMessage(List<String> toUsers, String message) throws Exception {
        JSONObject json = new JSONObject();
        JSONArray users = new JSONArray();
        for (int i = 0; i < toUsers.size(); i++) {
            users.put(toUsers.get(i));
        }
        // users.put(toUsers);
        json.put("touser", users);
        json.put("msgtype", "text");
        JSONObject content = new JSONObject();
        content.put("content", message);
        json.put("text", content);

        WechatToken token = this.getWechatAccessToken();
        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());

        logger.info("request data:{}", json.toString());
        HttpResult r = http.doJsonPost(SEND_MASS_CUSTOMER_MESSAGE, json.toString(), urlParams);
        WechatResponse response = new WechatResponse(new JSONObject(r.getBody()));

        return response;
    }

    @Override
    public WechatResponse uploadTmpMedia(String type, File file, String id) throws Exception {
        return this.uploadMedia(UPLOAD_TMP_MEDIA, type, file, id);
    }

    @Override
    public WechatResponse uploadMaterial(String type, File file, String id) throws Exception {
        return uploadMedia(UPLOAD_MATERIAL, type, file, id);
    }

    public WechatResponse getMaterialCount() throws Exception {
        WechatToken token = this.getWechatAccessToken();
        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());
        HttpResult r = http.doGet(GET_MATERIAL_COUNT, urlParams, null);
        WechatMaterialCount count = new WechatMaterialCount(new JSONObject(r.getBody()));
        return count;
    }

    private WechatResponse uploadMedia(String url, String type, File file, String id) throws Exception {
        WechatToken token = this.getWechatAccessToken();
        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());
        urlParams.put("type", type);
        HttpResult r = http.doMediaPost(url, file, id, urlParams);
        WechatMedia media = new WechatMedia(new JSONObject(r.getBody()));
        return media;
    }

    @Override
    public WechatResponse getMaterialList(String type, int offset, int count) throws Exception {
        WechatToken token = this.getWechatAccessToken();
        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());

        JSONObject body = new JSONObject();
        body.put("type", type);
        body.put("offset", offset);
        body.put("count", count);

        HttpResult r = http.doJsonPost(BATCHGET_MATERIAL, body.toString(), urlParams);

        WechatMediaSum sum = new WechatMediaSum(new JSONObject(r.getBody()));
        return sum;
    }

    @Override
    public WechatResponse deleteMaterial(String media_id) throws Exception {
        WechatToken token = this.getWechatAccessToken();
        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());

        JSONObject body = new JSONObject();
        body.put("media_id", media_id);

        HttpResult r = http.doJsonPost(DELETE_MATERIAL, body.toString(), urlParams);
        WechatResponse response = new WechatResponse(new JSONObject(r.getBody()));
        return response;
    }

    @Override
    public WechatMedia uploadNews(WechatNews news) throws Exception {
        WechatToken token = this.getWechatAccessToken();
        if (token.getAccessToken() == null) {
            return null;
        }
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("access_token", token.getAccessToken());

        JSONObject article = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < news.getItems().size(); i++) {
            JSONObject item = new JSONObject(news.getItems().get(i));
            array.put(item);
        }
        article.put("articles", array);
        logger.info("news body:{}", article.toString());
        HttpResult r = http.doJsonPost(ADD_NEWS, article.toString(), urlParams);
        return new WechatMedia(new JSONObject(r.getBody()));
    }
}