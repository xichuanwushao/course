package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Sms;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.SmsReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.SmsResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.SmsService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/sms")
@RestController//@Controller 如果接口返回Json 用RestController
public class SmsController {
    @Resource
    private SmsService smsService;

    public static final String BUSINESS_NAME="短信验证码";
    @RequestMapping("/test")
    public String sms(){
        return "success";
    }

    @RequestMapping("/all")
    public List<SmsResp> all(){
        return smsService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        smsService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody SmsReq smsReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(smsReq.getMobile(), "手机号");
        ValidatorUtil.length(smsReq.getMobile(), "手机号", 1, 50);
        ValidatorUtil.require(smsReq.getCode(), "验证码");
        ValidatorUtil.require(smsReq.getUse(), "用途");
        ValidatorUtil.require(smsReq.getAt(), "生成时间");
        ValidatorUtil.require(smsReq.getStatus(), "状态");

        CommonResp commonResp = new CommonResp();
        smsService.save(smsReq);
        commonResp.setContent(smsReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        smsService.delete(id);
        return commonResp;
    }
}
