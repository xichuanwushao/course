package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.CategoryReq;
import com.xichuan.server.req.CourseCategoryReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.CourseCategory;
import com.xichuan.server.domain.CourseCategoryExample;
import com.xichuan.server.mapper.CourseCategoryMapper;
import com.xichuan.server.resp.CourseCategoryResp;
import com.xichuan.server.util.CopyUtil;
import com.xichuan.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class CourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;
    public List<CourseCategoryResp> all() {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
//        courseCategoryExample.createCriteria().andIdEqualTo("1");
//        courseCategoryExample.setOrderByClause("id desc");
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        List<CourseCategoryResp> courseCategoryRespList =new ArrayList<CourseCategoryResp>() ;
        for(int i = 0;i<courseCategoryList.size();i++){
            CourseCategory courseCategory = courseCategoryList.get(i);
            CourseCategoryResp courseCategoryResp = new CourseCategoryResp();
            BeanUtils.copyProperties(courseCategory,courseCategoryResp);
            courseCategoryRespList.add(courseCategoryResp);
        }
        return courseCategoryRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
//        courseCategoryExample.createCriteria().andIdEqualTo("1");
//        courseCategoryExample.setOrderByClause("id desc");
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);//写在select的下一行
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageReq.setTotal(pageInfo.getTotal());
        List<CourseCategoryResp> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryResp.class);
        pageReq.setList(courseCategoryDtoList);
    }
    public void save(CourseCategoryReq courseCategoryReq) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryReq, CourseCategory.class);
        if(StringUtils.isEmpty(courseCategoryReq.getId())){
            this.insert(courseCategory);
        }else{
            this.update(courseCategory);
        }
    }
    public void insert(CourseCategory courseCategory) {
        courseCategory.setId(IdUtil.simpleUUID());
        courseCategoryMapper.insert(courseCategory);
    }
    public void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }
    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey( id);
    }

    /***
     * 根据某一课程，先清空课程分类，再保存课程分类
     * @param courseId
     * @param categoryReqList
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryReq> categoryReqList){
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        for(int num = 0 ; num < categoryReqList.size(); num++ ){
            CategoryReq categoryReq = categoryReqList.get(num);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryReq.getId());
            insert(courseCategory);
        }
    }

    /**
     * 查找课程下所有分类
     * @param courseId
     */
    public List<CourseCategoryReq> listByCourse(String courseId) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(courseCategoryList, CourseCategoryReq.class);
    }
}
