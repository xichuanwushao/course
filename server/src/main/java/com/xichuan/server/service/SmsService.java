package com.xichuan.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.enums.SmsStatusEnum;
import com.xichuan.server.exception.BusinessException;
import com.xichuan.server.exception.BusinessExceptionCode;
import com.xichuan.server.req.SmsReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Sms;
import com.xichuan.server.domain.SmsExample;
import com.xichuan.server.mapper.SmsMapper;
import com.xichuan.server.resp.SmsResp;
import com.xichuan.server.util.CopyUtil;
import com.xichuan.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
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
    /**
     * 发送短信验证码
     * 同手机号同操作1分钟内不能重复发送短信
     * @param smsReq
     */
    public void sendCode(SmsReq smsReq) {
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        // 查找1分钟内有没有同手机号同操作发送记录且没被用过
        criteria.andMobileEqualTo(smsReq.getMobile())
                .andUseEqualTo(smsReq.getUse())
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode())
                .andAtGreaterThan(new Date(new Date().getTime() - 1 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList == null || smsList.size() == 0) {
            saveAndSend(smsReq);
        } else {
            logger.warn("短信请求过于频繁, {}", smsReq.getMobile());
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_TOO_FREQUENT);
        }
    }

    /**
     * 保存并发送短信验证码
     * @param smsReq
     */
    private void saveAndSend(SmsReq smsReq) {
        // 生成6位数字
        String code = String.valueOf((int)(((Math.random() * 9) + 1) * 100000));
        smsReq.setAt(new Date());
        smsReq.setStatus(SmsStatusEnum.NOT_USED.getCode());
        smsReq.setCode(code);
        this.save(smsReq);

        // TODO 调第三方短信接口发送短信
    }
}
