package com.xichuan.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.exception.BusinessException;
import com.xichuan.server.exception.BusinessExceptionCode;
import com.xichuan.server.req.LoginMemberReq;
import com.xichuan.server.req.MemberReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Member;
import com.xichuan.server.domain.MemberExample;
import com.xichuan.server.mapper.MemberMapper;
import com.xichuan.server.resp.MemberResp;
import com.xichuan.server.util.CopyUtil;
import com.xichuan.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    @Resource
    private MemberMapper memberMapper;
    public List<MemberResp> all() {
        MemberExample memberExample = new MemberExample();
//        memberExample.createCriteria().andIdEqualTo("1");
//        memberExample.setOrderByClause("id desc");
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        List<MemberResp> memberRespList =new ArrayList<MemberResp>() ;
        for(int i = 0;i<memberList.size();i++){
            Member member = memberList.get(i);
            MemberResp memberResp = new MemberResp();
            BeanUtils.copyProperties(member,memberResp);
            memberRespList.add(memberResp);
        }
        return memberRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        MemberExample memberExample = new MemberExample();
//        memberExample.createCriteria().andIdEqualTo("1");
//        memberExample.setOrderByClause("id desc");
        List<Member> memberList = memberMapper.selectByExample(memberExample);//写在select的下一行
        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageReq.setTotal(pageInfo.getTotal());
        List<MemberResp> memberDtoList = CopyUtil.copyList(memberList, MemberResp.class);
        pageReq.setList(memberDtoList);
    }
    public void save(MemberReq memberReq) {
        Member member = CopyUtil.copy(memberReq, Member.class);
        if(StringUtils.isEmpty(memberReq.getId())){
            this.insert(member);
        }else{
            this.update(member);
        }
    }
    public void insert(Member member) {
        Date now = new Date();
        member.setId(UuidUtil.getShortUuid());
        member.setRegisterTime(now);
        memberMapper.insert(member);
    }
    public void update(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey( id);
    }


    /**
     * 按手机号查找
     * @param mobile
     * @return
     */
    public MemberReq findByMobile(String mobile) {
        Member member = this.selectByMobile(mobile);
        return CopyUtil.copy(member, MemberReq.class);
    }

    /**
     * 按手机号查找
     * @param mobile
     * @return
     */
    public Member selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }
    /**
     * 登录
     * @param memberReq
     */
    public LoginMemberReq login(MemberReq memberReq) {
        Member member = selectByMobile(memberReq.getMobile());
        if (member == null) {
            logger.info("手机号不存在, {}", memberReq.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberReq.getPassword())) {
                // 登录成功
                LoginMemberReq loginMemberDto = CopyUtil.copy(member, LoginMemberReq.class);
                return loginMemberDto;
            } else {
                logger.info("密码不对, 输入密码：{}, 数据库密码：{}", memberReq.getPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
            }
        }
    }
}
