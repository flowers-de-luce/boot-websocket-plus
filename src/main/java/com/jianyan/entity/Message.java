package com.jianyan.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import java.sql.Timestamp;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private long id;

    private String fromUser;

    private String toUser;

    private Object content;

    private Integer type;

    @TableField(exist = true)
    private Timestamp sendTime;


}
