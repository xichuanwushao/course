package com.xichuan.system.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.RoleUser;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.RoleUserReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.RoleUserResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.RoleUserService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/roleUser")
@RestController//@Controller 如果接口返回Json 用RestController
public class RoleUserController {
    @Resource
    private RoleUserService roleUserService;

    public static final String BUSINESS_NAME="角色用户关联";
    @RequestMapping("/test")
    public String roleUser(){
        return "success";
    }

    @RequestMapping("/all")
    public List<RoleUserResp> all(){
        return roleUserService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        roleUserService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody RoleUserReq roleUserReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(roleUserReq.getRoleId(), "角色");
        ValidatorUtil.require(roleUserReq.getUserId(), "用户");

        CommonResp commonResp = new CommonResp();
        roleUserService.save(roleUserReq);
        commonResp.setContent(roleUserReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        roleUserService.delete(id);
        return commonResp;
    }
}
