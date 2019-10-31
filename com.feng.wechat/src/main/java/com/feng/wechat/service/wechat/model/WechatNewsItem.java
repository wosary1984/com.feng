package com.feng.wechat.service.wechat.model;

import java.io.InputStream;

import org.json.JSONObject;

public class WechatNewsItem extends WechatResponse {
    private String thumb_media_id;
    private String thumb_url;
    private String url;
    private String item_id;
    private String author;
    private String title;
    private String content_source_url;
    private String content;
    private String digest;
    private int show_cover_pic = 1;
    private int need_open_comment = 1;
    private int only_fans_can_comment = 1;
    private String pic_name;
    private String description;
    private InputStream file_stream;

    public WechatNewsItem(JSONObject object) {
        super(object);
        this.setTitle(object.isNull("title") ? null : object.getString("title"));
        this.setAuthor(object.isNull("author") ? null : object.getString("author"));
        this.setDigest(object.isNull("digest") ? null : object.getString("digest"));
        this.setContent(object.isNull("content") ? null : object.getString("content"));
        this.setContent_source_url(object.isNull("content_source_url") ? null : object.getString("content_source_url"));
        this.setThumb_media_id(object.isNull("thumb_media_id") ? null : object.getString("thumb_media_id"));
        this.setShow_cover_pic(object.isNull("show_cover_pic") ? 0 : object.getInt("show_cover_pic"));

        this.setUrl(object.isNull("url") ? null : object.getString("url"));
        this.setThumb_url(object.isNull("thumb_url") ? null : object.getString("thumb_url"));
        this.setNeed_open_comment(object.isNull("need_open_comment") ? 0 : object.getInt("need_open_comment"));
        this.setOnly_fans_can_comment(
                object.isNull("only_fans_can_comment") ? 0 : object.getInt("only_fans_can_comment"));
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("title", this.getTitle());
        object.put("author", this.getAuthor());
        object.put("digest", this.getDigest());
        object.put("content", this.getContent());
        object.put("thumb_media_id", this.getThumb_media_id());
        object.put("show_cover_pic", this.getShow_cover_pic());
        object.put("content_source_url", this.getContent_source_url());
        object.put("need_open_comment", this.getNeed_open_comment());
        object.put("only_fans_can_comment", this.getOnly_fans_can_comment());
        return object;
    }

    public WechatNewsItem() {
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_source_url() {
        return content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public int getShow_cover_pic() {
        return show_cover_pic;
    }

    public void setShow_cover_pic(int show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }

    public int getNeed_open_comment() {
        return need_open_comment;
    }

    public void setNeed_open_comment(int need_open_comment) {
        this.need_open_comment = need_open_comment;
    }

    public int getOnly_fans_can_comment() {
        return only_fans_can_comment;
    }

    public void setOnly_fans_can_comment(int only_fans_can_comment) {
        this.only_fans_can_comment = only_fans_can_comment;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public InputStream getFile_stream() {
        return file_stream;
    }

    public void setFile_stream(InputStream file_stream) {
        this.file_stream = file_stream;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}