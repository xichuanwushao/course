package com.xichuan.file.controller;

import com.xichuan.file.config.FileApplication;
import com.xichuan.server.domain.Test;
import com.xichuan.server.req.FileReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.FileService;
import com.xichuan.server.service.TestService;
import com.xichuan.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.java2d.loops.FillPath;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

//@Controller 如果接口返回页面用Controller
@RestController//@Controller 如果接口返回Json 用RestController
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileApplication.class);
    @Value("${file.path}")
    private String FILE_PATH ;//= "F:\\Data\\JAVA\\course\\";
    @Value("${file.domain}")
    private String FILE_DOMAIN ;//= "http://127.0.0.1:9000/file/";
    public static final String BUSINESS_NAME="文件";

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public CommonResp test(@RequestParam MultipartFile file) throws IOException {

        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
       CommonResp commonResp = new CommonResp();
        logger.info("上传文件开始:{}",file);
        logger.info(file.getOriginalFilename());
        logger.info(String.valueOf(file.getSize()));
        //保存文件到本地
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        String key = UuidUtil.getShortUuid();
        String path = "teacher\\"+key+"."+suffix;
        String fullPath = FILE_PATH+path;
        File dest = new File(fullPath);
        file.transferTo(dest);
        logger.info(dest.getAbsolutePath());
        logger.info("保存文件记录");
        FileReq fileReq = new FileReq();
        fileReq.setPath(path);
        fileReq.setName(fileName);
        fileReq.setSize(Math.toIntExact(file.getSize()));
        fileReq.setSuffix(suffix);
        fileReq.setUse("");
        fileService.save(fileReq);

        commonResp.setContent(FILE_DOMAIN+ path);
       return  commonResp;
    }


}
