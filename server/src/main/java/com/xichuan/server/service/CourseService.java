package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.domain.CourseContent;
import com.xichuan.server.enums.CourseStatusEnum;
import com.xichuan.server.mapper.CourseContentMapper;
import com.xichuan.server.mapper.CourseMapperCust;
import com.xichuan.server.req.*;
import com.xichuan.server.domain.Course;
import com.xichuan.server.domain.CourseExample;
import com.xichuan.server.mapper.CourseMapper;
import com.xichuan.server.resp.ChapterResp;
import com.xichuan.server.resp.CourseResp;
import com.xichuan.server.resp.SectionResp;
import com.xichuan.server.resp.TeacherResp;
import com.xichuan.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseMapperCust courseMapperCust;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private SectionService sectionService;

    @Resource
    private CourseContentMapper courseContentMapper;



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
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//????????????select??????
        CourseExample courseExample = new CourseExample();
//        courseExample.createCriteria().andIdEqualTo("1");
//        courseExample.setOrderByClause("id desc");
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);//??????select????????????
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageReq.setTotal(pageInfo.getTotal());
        List<CourseResp> courseDtoList = CopyUtil.copyList(courseList, CourseResp.class);
        pageReq.setList(courseDtoList);
    }

    /***
     * ?????????????????????????????????
     * @param coursePageReq
     */
    public void listPageV2(CoursePageReq coursePageReq) {
        PageHelper.startPage(coursePageReq.getPage(),coursePageReq.getSize());//????????????select??????
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria exampleCriteria = courseExample.createCriteria();
        if(!StringUtils.isEmpty(coursePageReq.getStatus())){
            exampleCriteria.andStatusEqualTo(coursePageReq.getStatus());
        }
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);//??????select????????????
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        coursePageReq.setTotal(pageInfo.getTotal());
        List<CourseResp> courseDtoList = CopyUtil.copyList(courseList, CourseResp.class);
        coursePageReq.setList(courseDtoList);
    }


    /***
     * ?????????????????????????????????
     * @param coursePageReq
     */
    public void listPage(CoursePageReq coursePageReq) {
        PageHelper.startPage(coursePageReq.getPage(),coursePageReq.getSize());//????????????select??????
        List<CourseResp> coursePageReqs = courseMapperCust.list(coursePageReq);//??????select????????????
        PageInfo<CourseResp> pageInfo = new PageInfo<>(coursePageReqs);
        coursePageReq.setTotal(pageInfo.getTotal());
        coursePageReq.setList(coursePageReqs);
    }
    @Transactional
    public void save(CourseReq courseReq) {
        Course course = CopyUtil.copy(courseReq, Course.class);
        if(StringUtils.isEmpty(courseReq.getId())){
            this.insert(course);
        }else{
            this.update(course);
        }
        //????????????????????????
        courseCategoryService.saveBatch(course.getId(),courseReq.getCategorys());
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

    /**
     * ??????????????????
     * @param courseId
     * @return
     */
    public void updateTime(String courseId) {
        logger.info("?????????????????????{}", courseId);
        courseMapperCust.updateTime(courseId);
    }

    /***
     * ??????
     * @param sortReq
     */
    public void sort(SortReq sortReq) {
        // ??????????????????????????????
        courseMapperCust.updateSort(sortReq);
        // ?????????????????????
        if(sortReq.getNewSort() > sortReq.getOldSort()){
            courseMapperCust.moveSortsForward(sortReq);
        }
        // ????????????????????? ????????????????????????????????????
        if(sortReq.getNewSort() < sortReq.getOldSort()){
            courseMapperCust.moveSortsBackward(sortReq);
        }

    }


    /**
     * ??????????????????????????????????????????????????????????????????
     */
    public List<CourseReq> listNew(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        courseExample.setOrderByClause("created_at desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, CourseReq.class);
    }

    /**
     * ????????????????????????web?????????????????????????????????
     * @param id
     * @return
     */
    public CourseResp findCourse(String id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())) {
            return null;
        }
        CourseResp courseDto = CopyUtil.copy(course, CourseResp.class);

        // ????????????
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content != null) {
            courseDto.setContent(content.getContent());
        }

        // ??????????????????
        TeacherResp teacherDto = teacherService.findById(courseDto.getTeacherId());
        courseDto.setTeacher(teacherDto);

        // ???????????????
        List<ChapterResp> chapterDtoList = chapterService.listByCourse(id);
        courseDto.setChapters(chapterDtoList);

        // ???????????????
        List<SectionResp> sectionDtoList = sectionService.listByCourse(id);
        courseDto.setSections(sectionDtoList);

        return courseDto;
    }
}
