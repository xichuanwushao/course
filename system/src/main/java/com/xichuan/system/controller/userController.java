package com.xichuan.system.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.user;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.userReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.userResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.userService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("user")
@RestController//@Controller 如果接口返回Json 用RestController
public class userController {
    @Resource
    private userService userService;

    public static final String BUSINESS_NAME="用户";
    @RequestMapping("test")
    public String user(){
        return "success";
    }

    @RequestMapping("all")
    public List<userResp> all(){
        return userService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        userService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody userReq userReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(userReq.getLoginName(), "登录名");
        ValidatorUtil.length(userReq.getLoginName(), "登录名", 1, 50);
        ValidatorUtil.length(userReq.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userReq.getPassword(), "密码");

        CommonResp commonResp = new CommonResp();
        userService.save(userReq);
        commonResp.setContent(userReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        userService.delete(id);
        return commonResp;
    }
}
