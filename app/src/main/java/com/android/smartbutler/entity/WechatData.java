package com.android.smartbutler.entity;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.entity
 * 文件名：WechatData
 * 创建者：HY
 * 创建时间：2018/9/16 14:14
 * 描述：  微信精选数据类
 */

public class WechatData {

    //标题
    private String title;
    //出处
    private String source;
    //图片的url
    private String imgUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
