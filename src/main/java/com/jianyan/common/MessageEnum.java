package com.jianyan.common;

import lombok.Data;
import lombok.Setter;

public enum MessageEnum {
    SYSTEM(0, "系统消息"),
    USER(1,"单发消息"),
    GROUP(2,"群发消息")
    ;
    private Integer type;
    private String desc;

    MessageEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
