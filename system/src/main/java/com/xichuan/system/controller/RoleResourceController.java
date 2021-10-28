package com.xichuan.system.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.RoleResource;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.RoleResourceReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.RoleResourceResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.RoleResourceService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/roleResource")
@RestController//@Controller 如果接口返回Json 用RestController
public class RoleResourceController {
    @Resource
    private RoleResourceService roleResourceService;

    public static final String BUSINESS_NAME="角色资源关联";
    @RequestMapping("/test")
    public String roleResource(){
        return "success";
    }

    @RequestMapping("/all")
    public List<RoleResourceResp> all(){
        return roleResourceService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        roleResourceService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody RoleResourceReq roleResourceReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(roleResourceReq.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceReq.getResourceId(), "资源");

        CommonResp commonResp = new CommonResp();
        roleResourceService.save(roleResourceReq);
        commonResp.setContent(roleResourceReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        roleResourceService.delete(id);
        return commonResp;
    }
}
