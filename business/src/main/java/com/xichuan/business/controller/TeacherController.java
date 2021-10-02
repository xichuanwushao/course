package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Teacher;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.TeacherReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.TeacherResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.TeacherService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("teacher")
@RestController//@Controller 如果接口返回Json 用RestController
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    public static final String BUSINESS_NAME="讲师";
    @RequestMapping("/test")
    public String teacher(){
        return "success";
    }

    @RequestMapping("/all")
    public CommonResp all(){
        CommonResp commonResp = new CommonResp();
        List<TeacherResp>  teacherRespList = teacherService.all();
        commonResp.setContent(teacherRespList);
        return commonResp;
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        teacherService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody TeacherReq teacherReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(teacherReq.getName(), "姓名");
        ValidatorUtil.length(teacherReq.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherReq.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherReq.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherReq.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherReq.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherReq.getIntro(), "简介", 1, 500);

        CommonResp commonResp = new CommonResp();
        teacherService.save(teacherReq);
        commonResp.setContent(teacherReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        teacherService.delete(id);
        return commonResp;
    }
}
