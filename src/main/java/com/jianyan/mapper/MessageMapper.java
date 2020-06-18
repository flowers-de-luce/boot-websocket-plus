package com.jianyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianyan.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
