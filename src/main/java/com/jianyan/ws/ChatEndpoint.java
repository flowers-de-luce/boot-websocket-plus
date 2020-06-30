package com.jianyan.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianyan.common.MessageEnum;
import com.jianyan.entity.Message;
import com.jianyan.mapper.MessageMapper;
import com.jianyan.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatEndpoint {
    /* wensocket注入对象：
     * 1. 将要注入的bean对象设置成静态的变量，
      * 2. 在websocketConfig配置类中进行注入*/
    public static MessageMapper messageMapper;

    // 用来存储每一个对象所对应的ChatEndpoint对象
    private static Map<String,ChatEndpoint> onlineUsers= new ConcurrentHashMap<>();
    // 声明session对象，通过该对象可发送消息给指定的用户
    private Session session;
    // 声明一个httpSession对象，获取之前在httpSession中的用户名
    // 使用数需先将httpSession存储到EndpointConfig中
    private HttpSession httpSession;

    // 连接建立时被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        // 将局部的session对象赋值给成员session
        this.session = session;
        // 先存入 后取出  该httpSession为使用用户的httpSession
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;

        // 从httpSession对象中获取用户名
        String username = (String) httpSession.getAttribute("username");
        // 将当前对象存储到容器中
        onlineUsers.put(username,this);

        // 将当前在线用户的用户名推送给所有的客户端
        // 1.获取消息
        String message = MessageUtils.getMessage("system", null, getNames(),null, MessageEnum.SYSTEM.getType());
        // 2.调用方法进行系统消息的推送
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message){
        try {
            // 要将该消息推送给所有的客户端
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<String> getNames(){
        return onlineUsers.keySet();
    }

    // 接收到客户端发送的数据时被调用
    @OnMessage
    public void onMessage(String message, Session session){
        try {
            // 将字符串message转换为Message对象
            Message messageWS = JSONObject.parseObject(message, Message.class);
            //  获取消息发送者
             String username = messageWS.getFromUser();
            // 获取要消息接受者
            String toUser = messageWS.getToUser();
            // 获取消息数据文本
            String content = (String) messageWS.getContent();
            // 获取消息数据图片
            String image = (String)messageWS.getImage();

            // 获取当前登录的用户
            String usernameSeesion = (String) httpSession.getAttribute("username");

            // 获取推送给指定用户的消息格式的数据
            String messageSend = MessageUtils.getMessage(username, toUser, content,image,  MessageEnum.USER.getType());
            // 发送方处理数据
            onlineUsers.get(username).session.getBasicRemote().sendText(messageSend);
            // 接收方处理数据
            onlineUsers.get(toUser).session.getBasicRemote().sendText(messageSend);


            // 将消息存储数据库
            Message messageSQL = JSONObject.parseObject(messageSend, Message.class);
            int insert = messageMapper.insert(messageSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){

    }


}
