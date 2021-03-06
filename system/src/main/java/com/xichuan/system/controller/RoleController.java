package com.xichuan.system.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Role;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.RoleReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.RoleResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.RoleService;
import com.xichuan.server.util.ValidatorUtil;
import com.xichuan.system.config.SystemApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/role")
@RestController//@Controller 如果接口返回Json 用RestController
public class RoleController {
    @Resource
    private RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(SystemApplication.class);

    public static final String BUSINESS_NAME="角色";
    @RequestMapping("/test")
    public String role(){
        return "success";
    }

    @RequestMapping("/all")
    public List<RoleResp> all(){
        return roleService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        roleService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody RoleReq roleReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(roleReq.getName(), "角色");
        ValidatorUtil.length(roleReq.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleReq.getDesc(), "描述");
        ValidatorUtil.length(roleReq.getDesc(), "描述", 1, 100);

        CommonResp commonResp = new CommonResp();
        roleService.save(roleReq);
        commonResp.setContent(roleReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        roleService.delete(id);
        return commonResp;
    }

    /**
     * 保存资源
     * @param roleReq
     */
    @PostMapping("/save-resource")
    public CommonResp saveResource(@RequestBody RoleReq roleReq) {
        logger.info("保存角色资源关联开始");
        CommonResp<RoleReq> responseDto = new CommonResp<>();
        roleService.saveResource(roleReq);
        responseDto.setContent(roleReq);
        return responseDto;
    }


    /**
     * 加载已关联的资源
     */
    @GetMapping("/list-resource/{roleId}")
    public CommonResp listResource(@PathVariable String roleId) {
        logger.info("加载资源开始");
        CommonResp responseDto = new CommonResp<>();
        List<String> resourceIdList = roleService.listResource(roleId);
        responseDto.setContent(resourceIdList);
        return responseDto;
    }

    /**
     * 保存用户
     * @param roleReq
     */
    @PostMapping("/save-user")
    public CommonResp saveUser(@RequestBody RoleReq roleReq) {
        logger.info("保存角色用户关联开始");
        CommonResp<RoleReq> responseDto = new CommonResp<>();
        roleService.saveUser(roleReq);
        responseDto.setContent(roleReq);
        return responseDto;
    }

    /**
     * 加载用户
     * @param roleId
     */
    @GetMapping("/list-user/{roleId}")
    public CommonResp listUser(@PathVariable String roleId) {
        logger.info("加载用户开始");
        CommonResp responseDto = new CommonResp<>();
        List<String> userIdList = roleService.listUser(roleId);
        responseDto.setContent(userIdList);
        return responseDto;
    }
}
