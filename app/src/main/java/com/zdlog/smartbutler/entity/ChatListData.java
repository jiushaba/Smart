package com.zdlog.smartbutler.entity;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.entity
 * 文件名：ChatListData
 * 创建者：jiushaba
 * 创建时间：2017/4/12 0012下午 5:34
 * 描述： 对话列表的实体
 */
public class ChatListData {
    //类型
    private int type;
    //文本
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
