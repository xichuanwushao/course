package com.xichuan.server.service;

import com.xichuan.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.MemberCourseReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.MemberCourse;
import com.xichuan.server.domain.MemberCourseExample;
import com.xichuan.server.mapper.MemberCourseMapper;
import com.xichuan.server.resp.MemberCourseResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class MemberCourseService {
    @Resource
    private MemberCourseMapper memberCourseMapper;
    public List<MemberCourseResp> all() {
        MemberCourseExample memberCourseExample = new MemberCourseExample();
//        memberCourseExample.createCriteria().andIdEqualTo("1");
//        memberCourseExample.setOrderByClause("id desc");
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);
        List<MemberCourseResp> memberCourseRespList =new ArrayList<MemberCourseResp>() ;
        for(int i = 0;i<memberCourseList.size();i++){
            MemberCourse memberCourse = memberCourseList.get(i);
            MemberCourseResp memberCourseResp = new MemberCourseResp();
            BeanUtils.copyProperties(memberCourse,memberCourseResp);
            memberCourseRespList.add(memberCourseResp);
        }
        return memberCourseRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        MemberCourseExample memberCourseExample = new MemberCourseExample();
//        memberCourseExample.createCriteria().andIdEqualTo("1");
//        memberCourseExample.setOrderByClause("id desc");
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);//写在select的下一行
        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageReq.setTotal(pageInfo.getTotal());
        List<MemberCourseResp> memberCourseDtoList = CopyUtil.copyList(memberCourseList, MemberCourseResp.class);
        pageReq.setList(memberCourseDtoList);
    }
    public void save(MemberCourseReq memberCourseReq) {
        MemberCourse memberCourse = CopyUtil.copy(memberCourseReq, MemberCourse.class);
        if(StringUtils.isEmpty(memberCourseReq.getId())){
            this.insert(memberCourse);
        }else{
            this.update(memberCourse);
        }
    }
    public void insert(MemberCourse memberCourse) {
        Date now = new Date();
        memberCourse.setId(UuidUtil.getShortUuid());
        memberCourseMapper.insert(memberCourse);
    }
    public void update(MemberCourse memberCourse) {
        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey( id);
    }

}
