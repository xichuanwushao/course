package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Member;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.MemberReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.MemberResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.MemberService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("/member")
@RestController//@Controller 如果接口返回Json 用RestController
public class MemberController {
    @Resource
    private MemberService memberService;

    public static final String BUSINESS_NAME="会员";
    @RequestMapping("/test")
    public String member(){
        return "success";
    }

    @RequestMapping("/all")
    public List<MemberResp> all(){
        return memberService.all();
    }

    @PostMapping("/listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        memberService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody MemberReq memberReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.length(memberReq.getMobile(), "手机号", 1, 20);
        ValidatorUtil.require(memberReq.getPassword(), "密码");
        ValidatorUtil.length(memberReq.getName(), "昵称", 1, 50);
        ValidatorUtil.length(memberReq.getPhoto(), "头像url", 1, 200);

        CommonResp commonResp = new CommonResp();
        memberService.save(memberReq);
        commonResp.setContent(memberReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        memberService.delete(id);
        return commonResp;
    }
}
