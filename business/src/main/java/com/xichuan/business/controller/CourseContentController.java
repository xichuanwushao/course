package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.CourseContent;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.CourseContentReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.CourseContentResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CourseContentService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("courseContent")
@RestController//@Controller 如果接口返回Json 用RestController
public class CourseContentController {
    @Resource
    private CourseContentService courseContentService;

    public static final String BUSINESS_NAME="课程内容";
    @RequestMapping("test")
    public String courseContent(){
        return "success";
    }

    @RequestMapping("all")
    public List<CourseContentResp> all(){
        return courseContentService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        courseContentService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody CourseContentReq courseContentReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(courseContentReq.getContent(), "课程内容");

        CommonResp commonResp = new CommonResp();
        courseContentService.save(courseContentReq);
        commonResp.setContent(courseContentReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        courseContentService.delete(id);
        return commonResp;
    }
}
