package com.etech.demo.chat;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.websocket.Session;

@Data
public class Message {

    public static final String ENTER = "ENTER";
    public static final String SPEAK = "SPEAK";
    public static final String QUIT = "QUIT";

    private String type;//消息类型

    private String username; //发送人

    private String msg; //发送消息

    private int onlineCount; //在线用户数

    private Session session;//当前用户对象

    private String userId;//用户id

    public static String jsonStr(String type, String username, String msg, int onlineTotal,String id) {
        return JSON.toJSONString(new Message(type, username, msg, onlineTotal,id));
    }


    public Message() {
    }

    public Message(String type, String username, String msg, int onlineCount, String userId) {
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineCount = onlineCount;
        this.userId = userId;
    }


}
