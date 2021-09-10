package com.xichuan.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@Controller 如果接口返回页面用Controller
@RestController//@Controller 如果接口返回Json 用RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping("test")
    public String test(){
        return "success";
    }
}
