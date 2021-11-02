package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.TeacherReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Teacher;
import com.xichuan.server.domain.TeacherExample;
import com.xichuan.server.mapper.TeacherMapper;
import com.xichuan.server.resp.TeacherResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    public List<TeacherResp> all() {
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        List<TeacherResp> teacherRespList = CopyUtil.copyList(teacherList,TeacherResp.class);
        return teacherRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        TeacherExample teacherExample = new TeacherExample();
//        teacherExample.createCriteria().andIdEqualTo("1");
//        teacherExample.setOrderByClause("id desc");
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);//写在select的下一行
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageReq.setTotal(pageInfo.getTotal());
        List<TeacherResp> teacherDtoList = CopyUtil.copyList(teacherList, TeacherResp.class);
        pageReq.setList(teacherDtoList);
    }
    public void save(TeacherReq teacherReq) {
        Teacher teacher = CopyUtil.copy(teacherReq, Teacher.class);
        if(StringUtils.isEmpty(teacherReq.getId())){
            this.insert(teacher);
        }else{
            this.update(teacher);
        }
    }
    public void insert(Teacher teacher) {
        teacher.setId(IdUtil.simpleUUID());
        teacherMapper.insert(teacher);
    }
    public void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey( id);
    }

    /**
     * 查找
     * @param id
     */
    public TeacherResp findById(String id) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        return CopyUtil.copy(teacher, TeacherResp.class);
    }

}
