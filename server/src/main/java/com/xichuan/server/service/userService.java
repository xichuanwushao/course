package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.userReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.user;
import com.xichuan.server.domain.userExample;
import com.xichuan.server.mapper.userMapper;
import com.xichuan.server.resp.userResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class userService {
    @Resource
    private userMapper userMapper;
    public List<userResp> all() {
        userExample userExample = new userExample();
//        userExample.createCriteria().andIdEqualTo("1");
//        userExample.setOrderByClause("id desc");
        List<user> userList = userMapper.selectByExample(userExample);
        List<userResp> userRespList =new ArrayList<userResp>() ;
        for(int i = 0;i<userList.size();i++){
            user user = userList.get(i);
            userResp userResp = new userResp();
            BeanUtils.copyProperties(user,userResp);
            userRespList.add(userResp);
        }
        return userRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        userExample userExample = new userExample();
//        userExample.createCriteria().andIdEqualTo("1");
//        userExample.setOrderByClause("id desc");
        List<user> userList = userMapper.selectByExample(userExample);//写在select的下一行
        PageInfo<user> pageInfo = new PageInfo<>(userList);
        pageReq.setTotal(pageInfo.getTotal());
        List<userResp> userDtoList = CopyUtil.copyList(userList, userResp.class);
        pageReq.setList(userDtoList);
    }
    public void save(userReq userReq) {
        user user = CopyUtil.copy(userReq, user.class);
        if(StringUtils.isEmpty(userReq.getId())){
            this.insert(user);
        }else{
            this.update(user);
        }
    }
    public void insert(user user) {
        user.setId(IdUtil.simpleUUID());
        userMapper.insert(user);
    }
    public void update(user user) {
        userMapper.updateByPrimaryKey(user);
    }
    public void delete(String id) {
        userMapper.deleteByPrimaryKey( id);
    }

}
