package com.xichuan.business.controller.web;

import com.xichuan.server.req.MemberReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.MemberService;
import com.xichuan.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("webMemberController")
@RequestMapping("/web/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;



    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/register")
    public CommonResp register(@RequestBody MemberReq memberReq) {
        // 保存校验
        ValidatorUtil.require(memberReq.getMobile(), "手机号");
        ValidatorUtil.length(memberReq.getMobile(), "手机号", 11, 11);
        ValidatorUtil.require(memberReq.getPassword(), "密码");
        ValidatorUtil.length(memberReq.getName(), "昵称", 1, 50);
        ValidatorUtil.length(memberReq.getPhoto(), "头像url", 1, 200);

        // 密码加密
        memberReq.setPassword(DigestUtils.md5DigestAsHex(memberReq.getPassword().getBytes()));


        CommonResp commonResp = new CommonResp();
        memberService.save(memberReq);
        commonResp.setContent(memberReq);
        return commonResp;
    }


}
