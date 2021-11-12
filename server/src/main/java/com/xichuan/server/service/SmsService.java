package com.xichuan.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.SmsReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Sms;
import com.xichuan.server.domain.SmsExample;
import com.xichuan.server.mapper.SmsMapper;
import com.xichuan.server.resp.SmsResp;
import com.xichuan.server.util.CopyUtil;
import com.xichuan.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class SmsService {
    @Resource
    private SmsMapper smsMapper;
    public List<SmsResp> all() {
        SmsExample smsExample = new SmsExample();
//        smsExample.createCriteria().andIdEqualTo("1");
//        smsExample.setOrderByClause("id desc");
        List<Sms> smsList = smsMapper.selectByExample(smsExample);
        List<SmsResp> smsRespList =new ArrayList<SmsResp>() ;
        for(int i = 0;i<smsList.size();i++){
            Sms sms = smsList.get(i);
            SmsResp smsResp = new SmsResp();
            BeanUtils.copyProperties(sms,smsResp);
            smsRespList.add(smsResp);
        }
        return smsRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        SmsExample smsExample = new SmsExample();
//        smsExample.createCriteria().andIdEqualTo("1");
//        smsExample.setOrderByClause("id desc");
        List<Sms> smsList = smsMapper.selectByExample(smsExample);//写在select的下一行
        PageInfo<Sms> pageInfo = new PageInfo<>(smsList);
        pageReq.setTotal(pageInfo.getTotal());
        List<SmsResp> smsDtoList = CopyUtil.copyList(smsList, SmsResp.class);
        pageReq.setList(smsDtoList);
    }
    public void save(SmsReq smsReq) {
        Sms sms = CopyUtil.copy(smsReq, Sms.class);
        if(StringUtils.isEmpty(smsReq.getId())){
            this.insert(sms);
        }else{
            this.update(sms);
        }
    }
    public void insert(Sms sms) {
        Date now = new Date();
        sms.setId(UuidUtil.getShortUuid());
        smsMapper.insert(sms);
    }
    public void update(Sms sms) {
        smsMapper.updateByPrimaryKey(sms);
    }
    public void delete(String id) {
        smsMapper.deleteByPrimaryKey( id);
    }

}
