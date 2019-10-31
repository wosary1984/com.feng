package com.feng.wechat.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.feng.controller.BaseController;
import com.feng.http.service.common.HttpApiService;
import com.feng.wechat.service.wechat.WechatService;
import com.feng.wechat.service.wechat.model.WechatCustomMessageType;
import com.feng.wechat.service.wechat.model.WechatNews;
import com.feng.wechat.service.wechat.model.WechatNewsItem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController {

    @Resource
    private HttpApiService http;


    @Autowired
    private WechatService wechat;

    // @Autowired
    // private HttpServletRequest request;

    @RequestMapping("token")
    public String wechatToken() throws Exception {
        return success("token", 200, new JSONObject(wechat.getWechatAccessToken()));
    }

    @RequestMapping("users")
    public String users() throws Exception {
        return success("users", 200, new JSONObject(wechat.getUsers(null)));
    }

    @RequestMapping("profile")
    public String userProfile() throws Exception {
        String openid = "opRTZs5qDigjVT5Yjuc4fJ7yKRK0";
        return success("users", 200, new JSONObject(wechat.getUserProfie(openid)));
    }

    @RequestMapping("custom-message")
    public String customMessage() throws Exception {
        String openid = "opRTZs5qDigjVT5Yjuc4fJ7yKRK0";
        JSONObject o = new JSONObject().put("content", "Hello World Bruce");
        return success(" custom-message", 200, new JSONObject(wechat.sendTextCustomMessage(openid, o)));
    }

    @RequestMapping("custom-img")
    public String customImgMessage() throws Exception {
        String openid = "opRTZs5qDigjVT5Yjuc4fJ7yKRK0";
        String media_id = "hSB-MH7dXxo_YeAuNYARbmT8ETAcj4wXSptn482KJFs";
        JSONObject o = new JSONObject().put("media_id", media_id);
        return success("custom-img-message", 200,
                new JSONObject(wechat.sendCustomMessage(openid, WechatCustomMessageType.image, o)));
    }

    @RequestMapping("custom-mpnews")
    public String csutomMpnewsMessage() throws Exception {
        String openid = "opRTZs5qDigjVT5Yjuc4fJ7yKRK0";
        String media_id = "hSB-MH7dXxo_YeAuNYARbr1IRMpCcC0NfFKq8xk1G-w";
        JSONObject o = new JSONObject().put("media_id", media_id);
        return success("custom-message", 200,
                new JSONObject(wechat.sendCustomMessage(openid, WechatCustomMessageType.mpnews, o)));
    }

    @RequestMapping("custom-news")
    public String csutomNewsMessage() throws Exception {
        String openid = "opRTZs5qDigjVT5Yjuc4fJ7yKRK0";

        JSONArray a = new JSONArray();
        JSONObject o = new JSONObject().put("title", "Happy Day").put("description", "Is Really A Happy Day")
                .put("url", "www.baidu.com").put("picurl",
                        "http://mmbiz.qpic.cn/mmbiz_png/Y6BIIFhcpZBMfc9ThroGujohTeU9qVUwk5ThBVH5R6gwB67tfHMPyGNnocKxtWs2ocqnWZCm6WYA7qzuzLRczg/0?wx_fmt=png");
        a.put(o);
        JSONObject x = new JSONObject().put("articles", a);
        return success("custom-message", 200,
                new JSONObject(wechat.sendCustomMessage(openid, WechatCustomMessageType.news, x)));
    }

    @RequestMapping("custom-msgmenue")
    public String csutomMsgmenu() throws Exception {
        String openid = "opRTZs5qDigjVT5Yjuc4fJ7yKRK0";
        JSONArray a = new JSONArray();
        a.put(new JSONObject().put("id", "101").put("content", "满意"));
        a.put(new JSONObject().put("id", "102").put("content", "不满意"));
        JSONObject x = new JSONObject().put("head_content", "您对本次服务是否满意呢?").put("list", a).put("tail_content",
                "欢迎再次光临");
        return success("custom-msgmenue", 200,
                new JSONObject(wechat.sendCustomMessage(openid, WechatCustomMessageType.msgmenu, x)));
    }

    @RequestMapping("mass-message")
    public String massMessage() throws Exception {
        List<String> toUsers = new ArrayList<>();
        toUsers.add("opRTZs5qDigjVT5Yjuc4fJ7yKRK0");
        toUsers.add("opRTZs3rVLS1IByA8eTXLNzWt1xQ");
        return success("mass-message", 200,
                new JSONObject(wechat.sendMassCustomMessage(toUsers, "Hello World Wechat Mass")));
    }

    @RequestMapping("upload-media")
    public String uploadTmpMedia() throws Exception {
        File file = ResourceUtils.getFile("classpath:static/2019.png");
        return success("upload-media", 200, new JSONObject(wechat.uploadTmpMedia("image", file, "image")));
    }

    @RequestMapping("upload-material")
    public String uploadMaterial() throws Exception {
        File file = ResourceUtils.getFile("classpath:static/2019.png");
        return success("upload-material", 200, new JSONObject(wechat.uploadMaterial("thumb", file, "media")));
    }

    @RequestMapping("material-count")
    public String materialCount() throws Exception {
        return success("material-count", 200, new JSONObject(wechat.getMaterialCount()));
    }

    @RequestMapping("batch-get-material")
    public String batchGetMaterial() throws Exception {
        return success("batch-get-material", 200, new JSONObject(wechat.getMaterialList("image", 0, 10)));
    }

    @RequestMapping("batch-get-material-news")
    public String batchGetMaterialNews() throws Exception {
        return success("batch-get-material-news", 200, new JSONObject(wechat.getMaterialList("news", 0, 10)));
    }

    @RequestMapping("upload-news")
    public String uploadNews() throws Exception {

        WechatNews news = new WechatNews();

        WechatNewsItem item1 = new WechatNewsItem();
        item1.setTitle("Bruce Test");
        item1.setAuthor("BRUCE");
        item1.setThumb_media_id("hSB-MH7dXxo_YeAuNYARbmT8ETAcj4wXSptn482KJFs");
        item1.setDigest("This is for upload news test");
        item1.setContent("量子（quantum）是现代物理的重要概念。即一个物理量如果存在最小的不可分割的基本单位，则这个物理量是量子化的，并把最小单位称为量子");
        item1.setContent_source_url("www.sap.com");
        news.addItem(item1);

        return success("batch-get-material-new", 200, new JSONObject(wechat.uploadNews(news)));
    }

    @RequestMapping("del-material")
    public String deleteMaterial() throws Exception {
        String media_id = "hSB-MH7dXxo_YeAuNYARbotRWlv-m-CNIhoYc0PDIsU";
        return success("del-material", 200, new JSONObject(wechat.deleteMaterial(media_id)));
    }
}