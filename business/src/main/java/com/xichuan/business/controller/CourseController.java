package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Course;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.CourseReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.CourseResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CourseService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("course")
@RestController//@Controller 如果接口返回Json 用RestController
public class CourseController {
    @Resource
    private CourseService courseService;

    public static final String BUSINESS_NAME="课程";
    @RequestMapping("test")
    public String course(){
        return "success";
    }

    @RequestMapping("all")
    public List<CourseResp> all(){
        return courseService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        courseService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody CourseReq courseReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(courseReq.getName(), "名称");
        ValidatorUtil.length(courseReq.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseReq.getSummary(), "概述", 1, 2000);
        ValidatorUtil.require(courseReq.getPrice(), "价格(元)");
        ValidatorUtil.length(courseReq.getImage(), "封面", 1, 100);
        ValidatorUtil.require(courseReq.getLevel(), "级别");

        CommonResp commonResp = new CommonResp();
        courseService.save(courseReq);
        commonResp.setContent(courseReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        courseService.delete(id);
        return commonResp;
    }
}
