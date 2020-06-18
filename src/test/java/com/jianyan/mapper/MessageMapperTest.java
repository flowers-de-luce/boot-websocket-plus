package com.jianyan.mapper;

import com.jianyan.common.MessageEnum;
import com.jianyan.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageMapperTest {
    @Autowired
    public MessageMapper messageMapper;

    @Test

    public void insertMessage(){

            Message message = new Message();
            message.setFromUser("张三");
            message.setToUser("李wu");
            message.setContent("你好啊  李四");
            message.setType(MessageEnum.SYSTEM.getType());
            messageMapper.insert(message);

    }

    @Test
    public void   findList(){
        Message message = messageMapper.selectById(1);
        System.out.println(message);
    }


}