package com.xichuan.business.controller.web;

import com.xichuan.server.req.MemberCourseReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.MemberCourseService;
import com.xichuan.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("webMemberCourseController")
@RequestMapping("/web/member-course")
public class MemberCourseController {

    private static final Logger logger = LoggerFactory.getLogger(MemberCourseController.class);
    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private MemberCourseService memberCourseService;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/enroll")
    public CommonResp enroll(@RequestBody MemberCourseReq memberCourseReq) {
        // 保存校验
        ValidatorUtil.require(memberCourseReq.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseReq.getCourseId(), "课程id");

        CommonResp responseDto = new CommonResp();
        memberCourseReq = memberCourseService.enroll(memberCourseReq);
        responseDto.setContent(memberCourseReq);
        return responseDto;
    }


}
