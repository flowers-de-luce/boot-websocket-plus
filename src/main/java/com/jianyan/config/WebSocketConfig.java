package com.jianyan.config;

import com.jianyan.mapper.MessageMapper;
import com.jianyan.ws.ChatEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    /* wensocket注入对象：
     * 1. 将要注入的bean对象设置成静态的变量，
     * 2. 在websocketConfig配置类中进行注入*/
    @Autowired
    public void setMessageMapper(MessageMapper messageMapper){
        ChatEndpoint.messageMapper = messageMapper;
    }
}
