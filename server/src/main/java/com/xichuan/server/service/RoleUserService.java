package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.RoleUserReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.RoleUser;
import com.xichuan.server.domain.RoleUserExample;
import com.xichuan.server.mapper.RoleUserMapper;
import com.xichuan.server.resp.RoleUserResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class RoleUserService {
    @Resource
    private RoleUserMapper roleUserMapper;
    public List<RoleUserResp> all() {
        RoleUserExample roleUserExample = new RoleUserExample();
//        roleUserExample.createCriteria().andIdEqualTo("1");
//        roleUserExample.setOrderByClause("id desc");
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        List<RoleUserResp> roleUserRespList =new ArrayList<RoleUserResp>() ;
        for(int i = 0;i<roleUserList.size();i++){
            RoleUser roleUser = roleUserList.get(i);
            RoleUserResp roleUserResp = new RoleUserResp();
            BeanUtils.copyProperties(roleUser,roleUserResp);
            roleUserRespList.add(roleUserResp);
        }
        return roleUserRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        RoleUserExample roleUserExample = new RoleUserExample();
//        roleUserExample.createCriteria().andIdEqualTo("1");
//        roleUserExample.setOrderByClause("id desc");
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);//写在select的下一行
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageReq.setTotal(pageInfo.getTotal());
        List<RoleUserResp> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserResp.class);
        pageReq.setList(roleUserDtoList);
    }
    public void save(RoleUserReq roleUserReq) {
        RoleUser roleUser = CopyUtil.copy(roleUserReq, RoleUser.class);
        if(StringUtils.isEmpty(roleUserReq.getId())){
            this.insert(roleUser);
        }else{
            this.update(roleUser);
        }
    }
    public void insert(RoleUser roleUser) {
        roleUser.setId(IdUtil.simpleUUID());
        roleUserMapper.insert(roleUser);
    }
    public void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey( id);
    }

}
