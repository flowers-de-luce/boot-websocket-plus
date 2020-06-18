package com.jianyan.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class LoginController {

    /**
     * 登陆界面
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }
    /**
     * 聊天界面
     */
    @GetMapping("/index")
    public ModelAndView index(String username, String password, HttpServletRequest request, HttpSession session) throws UnknownHostException {
        if (StringUtils.isEmpty(username)) {
            return new ModelAndView("/login");
        }
        session.setAttribute("username", username);
        ModelAndView mav = new ModelAndView("/chat");
        mav.addObject("username", username);
        mav.addObject("webSocketUrl", "ws://"+ InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/chat");

        return mav;
    }

    /*  $.ajax({uri:"getusername"
     * 前端发起ajax请求，用来从session中获取当前登录的用户名并响应给浏览器*/
    @RequestMapping("/getusername")
    public String getusername(HttpSession session){
        String username = (String) session.getAttribute("username");
        return username;
    }
}
