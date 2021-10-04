package com.xichuan.file.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.File;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.FileReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.FileResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.FileService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("file")
@RestController//@Controller 如果接口返回Json 用RestController
public class FileController {
    @Resource
    private FileService fileService;

    public static final String BUSINESS_NAME="文件";
    @RequestMapping("test")
    public String file(){
        return "success";
    }

    @RequestMapping("all")
    public List<FileResp> all(){
        return fileService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        fileService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody FileReq fileReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(fileReq.getPath(), "相对路径");
        ValidatorUtil.length(fileReq.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(fileReq.getName(), "文件名", 1, 100);
        ValidatorUtil.length(fileReq.getSuffix(), "后缀", 1, 10);

        CommonResp commonResp = new CommonResp();
        fileService.save(fileReq);
        commonResp.setContent(fileReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        fileService.delete(id);
        return commonResp;
    }
}
