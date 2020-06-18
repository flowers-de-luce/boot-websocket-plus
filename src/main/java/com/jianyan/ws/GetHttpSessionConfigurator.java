package com.jianyan.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    // 在HandshakeRequest成员变量中封装了httpSession
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 将httpSession对象存储到配置对象中
        // 利用httpSession的字节码做唯一标识，标识httpSession
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
