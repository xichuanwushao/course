package com.xichuan.business.controller;

import com.xichuan.server.domain.Chapter;
import com.xichuan.server.resp.ChapterResp;
import com.xichuan.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller
@RequestMapping("chapter")
@RestController//@Controller 如果接口返回Json 用RestController
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @RequestMapping("test")
    public String chapter(){
        return "success";
    }

    @RequestMapping("list")
    public List<ChapterResp> chapterlist(){
       return chapterService.list();
    }
}
