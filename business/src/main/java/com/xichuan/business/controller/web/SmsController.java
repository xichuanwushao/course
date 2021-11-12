package com.xichuan.business.controller.web;

import com.xichuan.server.req.SmsReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("webSmsController")
@RequestMapping("/web/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public CommonResp send(@RequestBody SmsReq smsReq) {
        LOG.info("发送短信请求开始: {}", smsReq);
        CommonResp responseDto = new CommonResp();
        smsService.sendCode(smsReq);
        LOG.info("发送短信请求结束");
        return responseDto;
    }
}
