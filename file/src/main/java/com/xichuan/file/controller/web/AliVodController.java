package com.xichuan.file.controller.web;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.util.VodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

//@Controller 如果接口返回页面用Controller


@RestController("webVodController")
@RequestMapping("/web")
public class AliVodController {

    private static final Logger logger = LoggerFactory.getLogger(AliVodController.class);

    @Value("${vod.accessKeyId}")
    private String accessKeyId;

    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;

    public static final String BUSINESS_NAME = "VOD";

    @RequestMapping(value = "/get-auth/{vod}", method = RequestMethod.GET)
    public CommonResp getAuth(@PathVariable String vod) throws ClientException {
        logger.info("获取播放授权开始: ");
        CommonResp responseDto = new CommonResp();
        DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = VodUtil.getVideoPlayAuth(client, vod);
            logger.info("授权码 = {}", response.getPlayAuth());
            responseDto.setContent(response.getPlayAuth());
            //VideoMeta信息
            logger.info("VideoMeta = {}", JSON.toJSONString(response.getVideoMeta()));
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        logger.info("获取播放授权结束");
        return responseDto;
    }
}
