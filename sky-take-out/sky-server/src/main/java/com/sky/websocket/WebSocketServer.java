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

/**
 * WebSocket service
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // save session object
    private static Map<String, Session> sessionMap = new HashMap();

    /**
     * set up connection
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("Client：" + sid + " established connection");
        sessionMap.put(sid, session);
    }

    /**
     * receive client message
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("Received message from client：" + sid + " message: " + message);
    }

    /**
     * close connection
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("Disconnect: " + sid);
        sessionMap.remove(sid);
    }

    /**
     * group message
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // group message
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
