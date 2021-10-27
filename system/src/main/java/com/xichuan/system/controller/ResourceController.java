package com.xichuan.system.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.ResourceReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.ResourceResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.ResourceService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/resource")
@RestController//@Controller 如果接口返回Json 用RestController
public class ResourceController {

    @javax.annotation.Resource
    private ResourceService resourceService;

    public static final String BUSINESS_NAME="资源";
    @RequestMapping("/test")
    public String resource(){
        return "success";
    }

    @RequestMapping("/all")
    public List<ResourceResp> all(){
        return resourceService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        resourceService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody ResourceReq resourceReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(resourceReq.getName(), "名称");
        ValidatorUtil.length(resourceReq.getName(), "名称", 1, 100);
        ValidatorUtil.length(resourceReq.getPage(), "页面", 1, 50);
        ValidatorUtil.length(resourceReq.getRequest(), "请求", 1, 200);

        CommonResp commonResp = new CommonResp();
        resourceService.save(resourceReq);
        commonResp.setContent(resourceReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        resourceService.delete(id);
        return commonResp;
    }
}
