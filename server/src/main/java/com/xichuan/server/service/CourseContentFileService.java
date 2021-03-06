package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.CourseContentFileReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.CourseContentFile;
import com.xichuan.server.domain.CourseContentFileExample;
import com.xichuan.server.mapper.CourseContentFileMapper;
import com.xichuan.server.resp.CourseContentFileResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class CourseContentFileService {

    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    public List<CourseContentFileResp> listNoPage(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        List<CourseContentFileResp> fileRespList = CopyUtil.copyList(fileList, CourseContentFileResp.class);
        return fileRespList;
    }
    public List<CourseContentFileResp> all() {
        CourseContentFileExample courseContentFileExample = new CourseContentFileExample();
//        courseContentFileExample.createCriteria().andIdEqualTo("1");
//        courseContentFileExample.setOrderByClause("id desc");
        List<CourseContentFile> courseContentFileList = courseContentFileMapper.selectByExample(courseContentFileExample);
        List<CourseContentFileResp> courseContentFileRespList =new ArrayList<CourseContentFileResp>() ;
        for(int i = 0;i<courseContentFileList.size();i++){
            CourseContentFile courseContentFile = courseContentFileList.get(i);
            CourseContentFileResp courseContentFileResp = new CourseContentFileResp();
            BeanUtils.copyProperties(courseContentFile,courseContentFileResp);
            courseContentFileRespList.add(courseContentFileResp);
        }
        return courseContentFileRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//????????????select??????
        CourseContentFileExample courseContentFileExample = new CourseContentFileExample();
//        courseContentFileExample.createCriteria().andIdEqualTo("1");
//        courseContentFileExample.setOrderByClause("id desc");
        List<CourseContentFile> courseContentFileList = courseContentFileMapper.selectByExample(courseContentFileExample);//??????select????????????
        PageInfo<CourseContentFile> pageInfo = new PageInfo<>(courseContentFileList);
        pageReq.setTotal(pageInfo.getTotal());
        List<CourseContentFileResp> courseContentFileDtoList = CopyUtil.copyList(courseContentFileList, CourseContentFileResp.class);
        pageReq.setList(courseContentFileDtoList);
    }
    public void save(CourseContentFileReq courseContentFileReq) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileReq, CourseContentFile.class);
        if(StringUtils.isEmpty(courseContentFileReq.getId())){
            this.insert(courseContentFile);
        }else{
            this.update(courseContentFile);
        }
    }
    public void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(IdUtil.simpleUUID());
        courseContentFileMapper.insert(courseContentFile);
    }
    public void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey( id);
    }

}
