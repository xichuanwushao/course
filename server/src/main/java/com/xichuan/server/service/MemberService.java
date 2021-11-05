package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.MemberReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Member;
import com.xichuan.server.domain.MemberExample;
import com.xichuan.server.mapper.MemberMapper;
import com.xichuan.server.resp.MemberResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class MemberService {
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
        member.setId(IdUtil.simpleUUID());
        memberMapper.insert(member);
    }
    public void update(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey( id);
    }

}
