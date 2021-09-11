package com.xichuan.system.controller;

import com.xichuan.server.domain.Test;
import com.xichuan.server.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller
@RestController//@Controller 如果接口返回Json 用RestController
public class TestController {
    @Resource
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
