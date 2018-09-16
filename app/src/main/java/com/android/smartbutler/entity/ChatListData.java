package com.android.smartbutler.entity;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.entity
 * 文件名：ChatListData
 * 创建者：HY
 * 创建时间：2018/9/16 9:55
 * 描述：  对话列表的实体
 */

public class ChatListData {

    //type
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
