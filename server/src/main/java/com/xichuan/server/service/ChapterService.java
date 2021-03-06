package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.ChapterPageReq;
import com.xichuan.server.req.ChapterReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Chapter;
import com.xichuan.server.domain.ChapterExample;
import com.xichuan.server.mapper.ChapterMapper;
import com.xichuan.server.resp.ChapterResp;
import com.xichuan.server.util.CopyUtil;
import com.xichuan.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;
    public List<ChapterResp> all() {
        ChapterExample chapterExample = new ChapterExample();
//        chapterExample.createCriteria().andIdEqualTo("1");
//        chapterExample.setOrderByClause("id desc");
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        List<ChapterResp> chapterRespList =new ArrayList<ChapterResp>() ;
        for(int i = 0;i<chapterList.size();i++){
            Chapter chapter = chapterList.get(i);
            ChapterResp chapterResp = new ChapterResp();
            BeanUtils.copyProperties(chapter,chapterResp);
            chapterRespList.add(chapterResp);
        }
        return chapterRespList;
    }
    public void listPage(ChapterPageReq chapterPageReq) {
        PageHelper.startPage(chapterPageReq.getPage(),chapterPageReq.getSize());//对第一个select有用
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
//        chapterExample.createCriteria().andIdEqualTo("1");
//        chapterExample.setOrderByClause("id desc");
        if(!StringUtils.isEmpty(chapterPageReq.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageReq.getCourseId());
        }
        if(!StringUtils.isEmpty(chapterPageReq.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageReq.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);//写在select的下一行
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageReq.setTotal(pageInfo.getTotal());
        List<ChapterResp> chapterDtoList = CopyUtil.copyList(chapterList, ChapterResp.class);
        chapterPageReq.setList(chapterDtoList);
    }
    public void save(ChapterReq chapterReq) {
        System.out.println("chapterReq"+chapterReq.getId());
        System.out.println("chapterReq"+chapterReq.getId());
        System.out.println("chapterReq"+chapterReq.getId());
        Chapter chapter = CopyUtil.copy(chapterReq, Chapter.class);
        if(StringUtils.isEmpty(chapterReq.getId())){
            this.insert(chapter);
        }else{
            this.update(chapter);
        }
    }
    public void insert(Chapter chapter) {
//        chapter.setId(IdUtil.simpleUUID());
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }
    public void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey( id);
    }
    /**
     * 查询某一课程下的所有章
     */
    public List<ChapterResp> listByCourse(String courseId) {
        ChapterExample example = new ChapterExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        List<ChapterResp> chapterDtoList = CopyUtil.copyList(chapterList, ChapterResp.class);
        return chapterDtoList;
    }
}
