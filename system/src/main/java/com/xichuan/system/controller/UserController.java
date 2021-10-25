package com.xichuan.system.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.core.Constants;
import com.xichuan.server.domain.User;
import com.xichuan.server.domain.UserExample;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.UserReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.LoginUserResp;
import com.xichuan.server.resp.UserResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.UserService;
import com.xichuan.server.util.ValidatorUtil;
import com.xichuan.system.config.SystemApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("user")
@RestController//@Controller 如果接口返回Json 用RestController
public class UserController {
    @Resource
    private UserService userService;

    public static final String BUSINESS_NAME="用户";
    @RequestMapping("test")
    public String user(){
        return "success";
    }

    @RequestMapping("all")
    public List<UserResp> all(){
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
    public CommonResp save(@RequestBody UserReq userReq){
        // TODO 保存校验
        // 保存校验

        userReq.setPassword(DigestUtils.md5DigestAsHex(userReq.getPassword().getBytes()));

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

    @PostMapping("/save-password")
    public CommonResp savePassword(@RequestBody UserReq userReq){
        userReq.setPassword(DigestUtils.md5DigestAsHex(userReq.getPassword().getBytes()));
        CommonResp commonResp = new CommonResp();
        userService.savePassword(userReq);
        commonResp.setContent(userReq);
        return commonResp;
    }
;
    @PostMapping("/login")
    public CommonResp login(@RequestBody UserReq userReq, HttpServletRequest request){
        userReq.setPassword(DigestUtils.md5DigestAsHex(userReq.getPassword().getBytes()));
        CommonResp commonResp = new CommonResp();
        LoginUserResp loginUserResp = userService.login(userReq);
        request.getSession().setAttribute(Constants.LOGIN_USER,loginUserResp);
        commonResp.setContent(loginUserResp);
        return commonResp;
    }

    /***
     * 退出登录
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public CommonResp login(HttpServletRequest request){
        CommonResp commonResp = new CommonResp();
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return commonResp;
    }
}
