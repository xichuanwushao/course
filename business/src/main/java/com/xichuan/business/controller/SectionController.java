package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Section;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.SectionPageReq;
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


@RequestMapping("section")
@RestController//@Controller 如果接口返回Json 用RestController
public class SectionController {
    @Resource
    private SectionService sectionService;

    public static final String BUSINESS_NAME="小节";
    @RequestMapping("test")
    public String section(){
        return "success";
    }

    @RequestMapping("all")
    public List<SectionResp> all(){
        return sectionService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody SectionPageReq sectionPageReq){
        CommonResp commonResp = new CommonResp();
        ValidatorUtil.require(sectionPageReq.getCourseId(), "课程ID");
        ValidatorUtil.require(sectionPageReq.getChapterId(), "大章ID");
        sectionService.listPage(sectionPageReq);
        commonResp.setContent(sectionPageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody SectionReq sectionReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(sectionReq.getTitle(), "标题");
        ValidatorUtil.length(sectionReq.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionReq.getVideo(), "视频", 1, 200);

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
