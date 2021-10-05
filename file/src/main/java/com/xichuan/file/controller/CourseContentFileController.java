package com.xichuan.file.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.CourseContentFile;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.CourseContentFileReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.CourseContentFileResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CourseContentFileService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("courseContentFile")
@RestController//@Controller 如果接口返回Json 用RestController
public class CourseContentFileController {
    @Resource
    private CourseContentFileService courseContentFileService;

    public static final String BUSINESS_NAME="课程文件内容";
    @RequestMapping("test")
    public String courseContentFile(){
        return "success";
    }

    @RequestMapping("all")
    public List<CourseContentFileResp> all(){
        return courseContentFileService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        courseContentFileService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody CourseContentFileReq courseContentFileReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(courseContentFileReq.getCourseId(), "课程id");
        ValidatorUtil.length(courseContentFileReq.getUrl(), "地址", 1, 100);
        ValidatorUtil.length(courseContentFileReq.getName(), "文件名", 1, 100);

        CommonResp commonResp = new CommonResp();
        courseContentFileService.save(courseContentFileReq);
        commonResp.setContent(courseContentFileReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        courseContentFileService.delete(id);
        return commonResp;
    }
}
