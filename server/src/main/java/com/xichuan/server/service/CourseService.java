package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.CourseReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Course;
import com.xichuan.server.domain.CourseExample;
import com.xichuan.server.mapper.CourseMapper;
import com.xichuan.server.resp.CourseResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;
    public List<CourseResp> all() {
        CourseExample courseExample = new CourseExample();
//        courseExample.createCriteria().andIdEqualTo("1");
//        courseExample.setOrderByClause("id desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        List<CourseResp> courseRespList =new ArrayList<CourseResp>() ;
        for(int i = 0;i<courseList.size();i++){
            Course course = courseList.get(i);
            CourseResp courseResp = new CourseResp();
            BeanUtils.copyProperties(course,courseResp);
            courseRespList.add(courseResp);
        }
        return courseRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        CourseExample courseExample = new CourseExample();
//        courseExample.createCriteria().andIdEqualTo("1");
//        courseExample.setOrderByClause("id desc");
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);//写在select的下一行
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageReq.setTotal(pageInfo.getTotal());
        List<CourseResp> courseDtoList = CopyUtil.copyList(courseList, CourseResp.class);
        pageReq.setList(courseDtoList);
    }
    public void save(CourseReq courseReq) {
        Course course = CopyUtil.copy(courseReq, Course.class);
        if(StringUtils.isEmpty(courseReq.getId())){
            this.insert(course);
        }else{
            this.update(course);
        }
    }
    public void insert(Course course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(IdUtil.simpleUUID());
        courseMapper.insert(course);
    }
    public void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey( id);
    }

}
