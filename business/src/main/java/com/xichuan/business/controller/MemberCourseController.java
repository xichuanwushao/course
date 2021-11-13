package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.MemberCourse;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.MemberCourseReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.MemberCourseResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.MemberCourseService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/memberCourse")
@RestController//@Controller 如果接口返回Json 用RestController
public class MemberCourseController {
    @Resource
    private MemberCourseService memberCourseService;

    public static final String BUSINESS_NAME="会员课程报名";
    @RequestMapping("/test")
    public String memberCourse(){
        return "success";
    }

    @RequestMapping("/all")
    public List<MemberCourseResp> all(){
        return memberCourseService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        memberCourseService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody MemberCourseReq memberCourseReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(memberCourseReq.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseReq.getCourseId(), "课程id");
        ValidatorUtil.require(memberCourseReq.getAt(), "报名时间");

        CommonResp commonResp = new CommonResp();
        memberCourseService.save(memberCourseReq);
        commonResp.setContent(memberCourseReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        memberCourseService.delete(id);
        return commonResp;
    }
}
