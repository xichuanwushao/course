package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Section;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.SectionReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.SectionResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.SectionService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/section")
@RestController//@Controller 如果接口返回Json 用RestController
public class SectionController {
    @Resource
    private SectionService sectionService;

    public static final String BUSINESS_NAME="小节";
    @RequestMapping("/test")
    public String section(){
        return "success";
    }

    @RequestMapping("/all")
    public List<SectionResp> all(){
        return sectionService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        sectionService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody SectionReq sectionReq){
        // TODO 保存校验


        CommonResp commonResp = new CommonResp();
        sectionService.save(sectionReq);
        commonResp.setContent(sectionReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        sectionService.delete(id);
        return commonResp;
    }
}
