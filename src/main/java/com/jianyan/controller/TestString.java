package com.jianyan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestString {
    @RequestMapping("/string")
    public String testString(){
        return "================通联成功==================";
    }
}
