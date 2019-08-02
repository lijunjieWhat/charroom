package com.etech.demo.chat;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务器
 */
@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     *全部在线会话
     */
    private static Map<String, Session> onlineSessions =  new ConcurrentHashMap<>();


    /**
     *当客户端打开链接。添加会话对象。更新在线人数
     */
    @OnOpen
    public void onOpen(@PathParam(value = "username") String username, Session session) throws IOException {

        onlineSessions.put(session.getId(),session);
        sendMessageToAll(Message.jsonStr(Message.ENTER, username, "", onlineSessions.size(),session.getId()));
    }
    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session) {
        onlineSessions.remove(session.getId());
        sendMessageToAll(Message.jsonStr(Message.QUIT, "", "", onlineSessions.size(),session.getId()));
    }

    @OnMessage
    public void onMessage(String jsonStr,Session session){
        Message message = JSON.parseObject(jsonStr, Message.class);
        System.out.println(jsonStr);
        sendMessageToAll(Message.jsonStr(Message.SPEAK,message.getUsername(),message.getMsg(),onlineSessions.size(),session.getId()));
    }


    /**
     * 公共方法：发送信息给所有人
     * @param msg
     */
    private static  void sendMessageToAll(String msg){
        onlineSessions.forEach((id,session)->{
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
