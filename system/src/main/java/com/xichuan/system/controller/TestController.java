package com.xichuan.system.controller;

import com.xichuan.system.domain.Test;
import com.xichuan.system.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller 如果接口返回页面用Controller
@RestController//@Controller 如果接口返回Json 用RestController
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("test")
    public String test(){
        return "success";
    }

    @RequestMapping("testlist")
    public List<Test> testlist(){
       return testService.list();
    }
}
