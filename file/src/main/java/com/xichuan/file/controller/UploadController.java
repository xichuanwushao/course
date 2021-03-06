package com.xichuan.file.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.xichuan.file.config.FileApplication;
import com.xichuan.server.enums.FileUseEnum;
import com.xichuan.server.req.FileReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.resp.FileResp;
import com.xichuan.server.service.FileService;
import com.xichuan.server.util.Base64ToMultipartFile;
import com.xichuan.server.util.UuidUtil;
import com.xichuan.server.util.VodUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

//@Controller 如果接口返回页面用Controller
@RestController//@Controller 如果接口返回Json 用RestController
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileApplication.class);
    @Value("${file.path}")
    private String FILE_PATH ;//= "F:\\Data\\JAVA\\course\\";
    @Value("${file.domain}")
    private String FILE_DOMAIN ;//= "http://127.0.0.1:9000/file/";
    public static final String BUSINESS_NAME="文件";
    @Value("${vod.accessKeyId}")
    private String accessKeyId;

    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;
    @Resource
    private FileService fileService;

//    @PostMapping("/upload")
//    public CommonResp test(@RequestParam MultipartFile shard,
//                           String use,
//                           String name,
//                           String suffix,
//                           Integer size,
//                           Integer shardIndex,
//                           Integer shardSize,
//                           Integer shardTotal,
//                           String key) throws IOException {
    @PostMapping("/upload")
    public CommonResp test(@RequestBody FileReq fileReq ) throws IOException {
        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);

        String use = fileReq.getUse();
        String key = fileReq.getKey();
        String suffix = fileReq.getSuffix();
        String shardBase64 = fileReq.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);
        String name = fileReq.getName();
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



//        String path = dir+File.separator+fileNameNoSuffix+"_"+key+"."+suffix;
        String path = new StringBuilder(dir).append(File.separator).append(fileNameNoSuffix)
                .append("_").append(key).append(".").append(suffix).toString();
        String localpath = new StringBuilder(path).append(".").append(fileReq.getShardIndex()).toString();

        String fullPath = FILE_PATH+localpath;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        logger.info(dest.getAbsolutePath());
        logger.info("保存文件记录");
        fileReq.setPath(path);
        fileService.save(fileReq);

        CommonResp commonResp = new CommonResp();
        fileReq.setPath(FILE_DOMAIN + path);
        commonResp.setContent(fileReq);
        if(fileReq.getShardIndex().equals( fileReq.getShardTotal()) ){
            this.merge(fileReq);
        }
        return  commonResp;
    }
    public void merge( FileReq fileReq ) {
        logger.info("合并分片开始");
        String path = fileReq.getPath().replace(FILE_DOMAIN,"");
        Integer shardTotal = fileReq.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = null;//文件追加写入
        try {
            outputStream = new FileOutputStream(newFile, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for( int i = 1 ; i <= shardTotal ; i++ ){
                // 读取第i个分片
                fileInputStream = new FileInputStream(new File(FILE_PATH + path+"."+i)); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
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

        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 删除分片
        logger.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            logger.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        logger.info("删除分片结束");

    }
    @GetMapping("/mergetest")
    public void mergetest() {
        logger.info("合并分片开始");
//        String path = fileDto.getPath(); //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
//        path = path.replace(FILE_DOMAIN, ""); //course\6sfSqfOwzmik4A4icMYuUe.mp4
//        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + "/course/123.mp4");
        FileOutputStream outputStream = null;//文件追加写入
        try {
            outputStream = new FileOutputStream(newFile, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
    @GetMapping("/check/{key}")
    public CommonResp check(@PathVariable String key){
        logger.info("检查上传分片开始：{}",key);
        CommonResp commonResp = new CommonResp();
        FileResp fileResp = fileService.findByKey(key);
        if(fileResp !=null){
            fileResp.setPath(FILE_DOMAIN+fileResp.getPath());
        }
        commonResp.setContent(fileResp);
        return commonResp;
    }

    @GetMapping("/checkvod/{key}")
    public CommonResp checkvod(@PathVariable String key){
        logger.info("检查上传分片开始：{}", key);
        CommonResp responseDto = new CommonResp();
        FileResp fileResp = fileService.findByKey(key);
        if (fileResp != null) {
            if (StringUtils.isEmpty(fileResp.getVod())) {
                fileResp.setPath(FILE_DOMAIN + fileResp.getPath());
            } else {
                DefaultAcsClient vodClient = null;
                GetMezzanineInfoResponse response = null;
                try {
                    vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
                    response = VodUtil.getMezzanineInfo(vodClient, fileResp.getVod());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("获取视频信息, response : " + JSON.toJSONString(response));
                String fileUrl = response.getMezzanine().getFileURL();
                fileResp.setPath(fileUrl);
            }
        }
        responseDto.setContent(fileResp);
        return responseDto;
    }
    @PostMapping("/upload-image")
    public CommonResp test(@RequestParam MultipartFile file,String use) throws IOException {

        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_PATH"+FILE_PATH);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
        System.out.println("UploadController FILE_DOMAIN"+FILE_DOMAIN);
        logger.info("上传文件开始:{}",file);
        logger.info(file.getOriginalFilename());
        logger.info(String.valueOf(file.getSize()));
        //保存文件到本地
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        String fileName = file.getOriginalFilename();
        if(fileName.lastIndexOf(".")==-1){
            fileName = fileName +".blob";
        }
        String fileNameNoSuffix = fileName.substring(0,fileName.lastIndexOf(".")).toLowerCase();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        String key = UuidUtil.getShortUuid();

        //如果文件夹不存在则创建
        String dir = fileUseEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }



        String path = dir+File.separator+fileNameNoSuffix+"_"+key+"."+suffix;
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
        fileReq.setUse(use);
        fileReq.setKey(key);
        fileService.save(fileReq);

        CommonResp commonResp = new CommonResp();
        fileReq.setPath(FILE_DOMAIN + path);
        commonResp.setContent(fileReq);
        return  commonResp;
    }
}
