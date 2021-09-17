package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.SectionReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Section;
import com.xichuan.server.domain.SectionExample;
import com.xichuan.server.mapper.SectionMapper;
import com.xichuan.server.resp.SectionResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;
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
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        SectionExample sectionExample = new SectionExample();
//        sectionExample.createCriteria().andIdEqualTo("1");
//        sectionExample.setOrderByClause("id desc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);//写在select的下一行
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageReq.setTotal(pageInfo.getTotal());
        List<SectionResp> sectionDtoList = CopyUtil.copyList(sectionList, SectionResp.class);
        pageReq.setList(sectionDtoList);
    }
    public void save(SectionReq sectionReq) {
        System.out.println("sectionReq"+sectionReq.getId());
        System.out.println("sectionReq"+sectionReq.getId());
        System.out.println("sectionReq"+sectionReq.getId());
        Section section = CopyUtil.copy(sectionReq, Section.class);
        if(StringUtils.isEmpty(sectionReq.getId())){
            this.insert(section);
        }else{
            this.update(section);
        }
    }
    public void insert(Section section) {
        section.setId(IdUtil.simpleUUID());
        sectionMapper.insert(section);
    }
    public void update(Section section) {
        sectionMapper.updateByPrimaryKey(section);
    }
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey( id);
    }

}
