package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.CourseContentReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.CourseContent;
import com.xichuan.server.domain.CourseContentExample;
import com.xichuan.server.mapper.CourseContentMapper;
import com.xichuan.server.resp.CourseContentResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class CourseContentService {
    @Resource
    private CourseContentMapper courseContentMapper;
    public List<CourseContentResp> all() {
        CourseContentExample courseContentExample = new CourseContentExample();
//        courseContentExample.createCriteria().andIdEqualTo("1");
//        courseContentExample.setOrderByClause("id desc");
        List<CourseContent> courseContentList = courseContentMapper.selectByExample(courseContentExample);
        List<CourseContentResp> courseContentRespList =new ArrayList<CourseContentResp>() ;
        for(int i = 0;i<courseContentList.size();i++){
            CourseContent courseContent = courseContentList.get(i);
            CourseContentResp courseContentResp = new CourseContentResp();
            BeanUtils.copyProperties(courseContent,courseContentResp);
            courseContentRespList.add(courseContentResp);
        }
        return courseContentRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        CourseContentExample courseContentExample = new CourseContentExample();
//        courseContentExample.createCriteria().andIdEqualTo("1");
//        courseContentExample.setOrderByClause("id desc");
        List<CourseContent> courseContentList = courseContentMapper.selectByExample(courseContentExample);//写在select的下一行
        PageInfo<CourseContent> pageInfo = new PageInfo<>(courseContentList);
        pageReq.setTotal(pageInfo.getTotal());
        List<CourseContentResp> courseContentDtoList = CopyUtil.copyList(courseContentList, CourseContentResp.class);
        pageReq.setList(courseContentDtoList);
    }
    public void save(CourseContentReq courseContentReq) {
        CourseContent courseContent = CopyUtil.copy(courseContentReq, CourseContent.class);
        if(StringUtils.isEmpty(courseContentReq.getId())){
            this.insert(courseContent);
        }else{
            this.update(courseContent);
        }
    }
    public void insert(CourseContent courseContent) {
        courseContent.setId(IdUtil.simpleUUID());
        courseContentMapper.insert(courseContent);
    }
    public void update(CourseContent courseContent) {
        courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
    }
    public void delete(String id) {
        courseContentMapper.deleteByPrimaryKey( id);
    }

}
