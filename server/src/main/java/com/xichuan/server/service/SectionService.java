package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.SectionPageReq;
import com.xichuan.server.req.SectionReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Section;
import com.xichuan.server.domain.SectionExample;
import com.xichuan.server.mapper.SectionMapper;
import com.xichuan.server.resp.SectionResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private CourseService courseService;

    public List<SectionResp> all() {
        SectionExample sectionExample = new SectionExample();
//        sectionExample.createCriteria().andIdEqualTo("1");
//        sectionExample.setOrderByClause("id desc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        List<SectionResp> sectionRespList =new ArrayList<SectionResp>() ;
        for(int i = 0;i<sectionList.size();i++){
            Section section = sectionList.get(i);
            SectionResp sectionResp = new SectionResp();
            BeanUtils.copyProperties(section,sectionResp);
            sectionRespList.add(sectionResp);
        }
        return sectionRespList;
    }
    public void listPage(SectionPageReq sectionPageReq) {
        PageHelper.startPage(sectionPageReq.getPage(),sectionPageReq.getSize());//对第一个select有用
        SectionExample sectionExample = new SectionExample();
//        sectionExample.createCriteria().andIdEqualTo("1");
//        sectionExample.setOrderByClause("id desc");
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageReq.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageReq.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageReq.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageReq.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageReq.setTotal(pageInfo.getTotal());
        List<SectionResp> sectionDtoList = CopyUtil.copyList(sectionList, SectionResp.class);
        sectionPageReq.setList(sectionDtoList);
    }
    @Transactional (rollbackFor = Exception.class )//抛出exception异常时 也可以触发事务
    public void save(SectionReq sectionReq) {
        Section section = CopyUtil.copy(sectionReq, Section.class);
        if(StringUtils.isEmpty(sectionReq.getId())){
            this.insert(section);
        }else{
            this.update(section);
        }
        courseService.updateTime(sectionReq.getCourseId());

    }
    public void insert(Section section) {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(IdUtil.simpleUUID());
        sectionMapper.insert(section);
    }
    public void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey( id);
    }

}
