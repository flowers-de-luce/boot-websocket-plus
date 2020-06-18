package com.jianyan.utils;

import com.alibaba.fastjson.JSON;
import com.jianyan.entity.Message;

public class MessageUtils {
    public static String getMessage(String fromUser, String toUser, Object content, Integer type){
        try {
            Message message = new Message();
            if ("" == fromUser && fromUser == null) {
                message.setFromUser("system");
            }else {
                message.setFromUser(fromUser);
            }
            message.setToUser(toUser);
            message.setContent(content);
            message.setType(type);
            return JSON.toJSONString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
