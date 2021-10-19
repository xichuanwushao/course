package com.xichuan.file.controller;

import com.xichuan.file.config.FileApplication;
import com.xichuan.server.enums.FileUseEnum;
import com.xichuan.server.req.FileReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.FileService;
import com.xichuan.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public CommonResp test(@RequestParam MultipartFile shard,
                           String use,
                           String name,
                           String suffix,
                           Integer size,
                           Integer shardIndex,
                           Integer shardSize,
                           Integer shardTotal,
                           String key) throws IOException {

        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
        logger.info("上传文件开始:{}", shard);
        logger.info(shard.getOriginalFilename());
        logger.info(String.valueOf(shard.getSize()));
        //保存文件到本地
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        if(name.lastIndexOf(".")==-1){
            name = name +".blob";
        }
        String fileNameNoSuffix = name.substring(0,name.lastIndexOf(".")).toLowerCase();
//        String key = UuidUtil.getShortUuid();

        //如果文件夹不存在则创建
        String dir = fileUseEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }



        String path = dir+File.separator+fileNameNoSuffix+"_"+key+"."+suffix;
        String fullPath = FILE_PATH+path;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        logger.info(dest.getAbsolutePath());
        logger.info("保存文件记录");
        FileReq fileReq = new FileReq();
        fileReq.setPath(path);
        fileReq.setName(name);
        fileReq.setSize(size);
        fileReq.setSuffix(suffix);
        fileReq.setUse(use);
        fileReq.setShardIndex(shardIndex);
        fileReq.setShardSize(shardSize);
        fileReq.setShardTotal(shardTotal);
        fileReq.setKey(key);
        fileService.save(fileReq);

        CommonResp commonResp = new CommonResp();
        fileReq.setPath(FILE_DOMAIN + path);
        commonResp.setContent(fileReq);
        return  commonResp;
    }
    @GetMapping("/merge")
    public void merge() throws Exception {
        logger.info("合并分片开始");
//        String path = fileDto.getPath(); //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
//        path = path.replace(FILE_DOMAIN, ""); //course\6sfSqfOwzmik4A4icMYuUe.mp4
//        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + "/course/123.mp4");
        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            // 读取第i个分片
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/blob_HywsnOPh.blob")); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
            while ((len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }
            // 读取第i个分片
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/blob_kI9glt3O.blob")); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
            while ((len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }
        } catch (IOException e) {
            logger.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                logger.info("IO流关闭");
            } catch (Exception e) {
                logger.error("IO流关闭", e);
            }
        }
        logger.info("合并分片结束");


    }
}
