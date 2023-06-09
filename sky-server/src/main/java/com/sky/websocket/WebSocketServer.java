package com.sky.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // 存放会话对象
    private final Map<String, Session> sessionMap = new HashMap<>();

    /**
     * 连接建立时调用
     *
     * @param session 会话
     * @param sid 会话Id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        sessionMap.put(sid, session);
    }

    /**
     * 收到消息时调用
     *
     * @param message 客户端发送的消息
     */
    @OnMessage
    public void oneMessage(String message, @PathParam("sid") String sid) {

    }

    /**
     * 连接关闭时调用的方法
     *
     * @param sid 会话Id
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        sessionMap.remove(sid);
    }

    /**
     * 给所有客户端发送消息
     *
     * @param message 信息
     */
    public void sendToAllClient(String message) {

        Collection<Session> sessions = sessionMap.values();

        // 遍历所有session
        for (Session session : sessions) {
            try {
                // 向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
