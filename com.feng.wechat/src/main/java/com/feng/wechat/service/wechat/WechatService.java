package com.feng.wechat.service.wechat;

import java.io.File;
import java.util.List;

import com.feng.wechat.service.wechat.model.WechatCustomMessageType;
import com.feng.wechat.service.wechat.model.WechatMedia;
import com.feng.wechat.service.wechat.model.WechatNews;
import com.feng.wechat.service.wechat.model.WechatResponse;
import com.feng.wechat.service.wechat.model.WechatToken;
import com.feng.wechat.service.wechat.model.WechatUserList;

public interface WechatService {
    public final String CHARSET_UTF_8 = "UTF-8";
    public final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    public final String GET_USERS = "https://api.weixin.qq.com/cgi-bin/user/get";
    public final String GET_USER_PROFILE = "https://api.weixin.qq.com/cgi-bin/user/info";
    public final String SEND_CUSTOMER_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    public final String SEND_MASS_CUSTOMER_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
    public final String UPLOAD_TMP_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload";
    public final String GET_TMP_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get";

    public final String UPLOAD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material";
    public final String GET_MATERIAL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
    public final String ADD_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news";
    public final String UPDATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/update_news";
    public final String BATCHGET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
    public final String DELETE_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material";

    public WechatToken getWechatAccessToken() throws Exception;

    public WechatResponse getUsers(WechatUserList users) throws Exception;

    public WechatResponse getUserProfie(String openid) throws Exception;

    public WechatResponse sendTextCustomMessage(String toUser, Object value) throws Exception;

    /**
     * Send custom meessae if type is text, value is message content of jsonobject
     * 
     * @param toUser
     * @param type
     * @param value
     * @return
     * @throws Exception
     */
    public WechatResponse sendCustomMessage(String toUser, WechatCustomMessageType type, Object value) throws Exception;

    public WechatResponse sendMassCustomMessage(List<String> toUsers, String message) throws Exception;

    public WechatResponse uploadTmpMedia(String type, File file, String id) throws Exception;

    public WechatResponse getMaterialCount() throws Exception;

    public WechatResponse getMaterialList(String type, int offset, int count) throws Exception;

    public WechatResponse uploadMaterial(String type, File file, String id) throws Exception;

    public WechatResponse deleteMaterial(String media_id) throws Exception;

    public WechatMedia uploadNews(WechatNews news) throws Exception;

}