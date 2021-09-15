package com.xichuan.server.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.xichuan.server.domain.Chapter;
import com.xichuan.server.domain.ChapterExample;
import com.xichuan.server.mapper.ChapterMapper;
import com.xichuan.server.resp.ChapterResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    public List<ChapterResp> list() {
        PageHelper.startPage(1,2);
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
}
