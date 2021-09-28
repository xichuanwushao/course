package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.CourseCategory;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.CourseCategoryReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.CourseCategoryResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CourseCategoryService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("courseCategory")
@RestController//@Controller 如果接口返回Json 用RestController
public class CourseCategoryController {
    @Resource
    private CourseCategoryService courseCategoryService;

    public static final String BUSINESS_NAME="课程分类";
    @RequestMapping("test")
    public String courseCategory(){
        return "success";
    }

    @RequestMapping("all")
    public List<CourseCategoryResp> all(){
        return courseCategoryService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        courseCategoryService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody CourseCategoryReq courseCategoryReq){
        // TODO 保存校验
        // 保存校验

        CommonResp commonResp = new CommonResp();
        courseCategoryService.save(courseCategoryReq);
        commonResp.setContent(courseCategoryReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        courseCategoryService.delete(id);
        return commonResp;
    }
}
