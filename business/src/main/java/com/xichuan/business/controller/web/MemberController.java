package com.xichuan.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.xichuan.server.exception.BusinessException;
import com.xichuan.server.req.LoginMemberReq;
import com.xichuan.server.req.MemberReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.MemberService;
import com.xichuan.server.util.UuidUtil;
import com.xichuan.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController("webMemberController")
@RequestMapping("/web/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

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

    /**
     * 登录
     */
    @PostMapping("/login")
    public CommonResp login(@RequestBody MemberReq memberReq) {
        logger.info("用户登录开始");
        memberReq.setPassword(DigestUtils.md5DigestAsHex(memberReq.getPassword().getBytes()));
        CommonResp responseDto = new CommonResp();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        String imageCode = (String) redisTemplate.opsForValue().get(memberReq.getImageCodeToken());
        logger.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            logger.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(memberReq.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            logger.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
            redisTemplate.delete(memberReq.getImageCodeToken());
        }

        LoginMemberReq loginMemberDto = memberService.login(memberReq);
        String token = UuidUtil.getShortUuid();
        loginMemberDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginMemberDto), 3600, TimeUnit.SECONDS);
        responseDto.setContent(loginMemberDto);
        return responseDto;
    }
    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp responseDto = new CommonResp();
        redisTemplate.delete(token);
        logger.info("从redis中删除token:{}", token);
        return responseDto;
    }

    /**
     * 校验手机号是否存在
     * 存在则success=true，不存在则success=false
     */
    @GetMapping(value = "/is-mobile-exist/{mobile}")
    public CommonResp isMobileExist(@PathVariable(value = "mobile") String mobile) throws BusinessException {
        logger.info("查询手机号是否存在开始");
        CommonResp responseDto = new CommonResp();
        MemberReq memberDto = memberService.findByMobile(mobile);
        if (memberDto == null) {
            responseDto.setSuccess(false);
        } else {
            responseDto.setSuccess(true);
        }
        return responseDto;
    }

}
