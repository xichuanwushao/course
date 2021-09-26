package com.xichuan.business.controller;

import com.xichuan.server.req.ChapterPageReq;
import com.xichuan.server.req.ChapterReq;
import com.xichuan.server.resp.ChapterResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.ChapterService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/chapter")
@RestController//@Controller 如果接口返回Json 用RestController
public class ChapterController {
    @Resource
    private ChapterService chapterService;
    public static final String BUSINESS_NAME="大章相关业务接口";
    @RequestMapping("/test")
    public String chapter(){
        return "success";
    }

    @RequestMapping("/all")
    public List<ChapterResp> all(){
        return chapterService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody ChapterPageReq chapterPageReq){
        CommonResp commonResp = new CommonResp();
        ValidatorUtil.require(chapterPageReq.getCourseId(), "课程ID");
        chapterService.listPage(chapterPageReq);
        commonResp.setContent(chapterPageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody ChapterReq chapterReq){
        // 保存校验
        ValidatorUtil.require(chapterReq.getName(), "名称");
        ValidatorUtil.require(chapterReq.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterReq.getCourseId(), "课程ID", 1, 8);

        CommonResp commonResp = new CommonResp();
        chapterService.save(chapterReq);
        commonResp.setContent(chapterReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        chapterService.delete(id);
        return commonResp;
    }
}
