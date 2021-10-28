package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.RoleReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Role;
import com.xichuan.server.domain.RoleExample;
import com.xichuan.server.mapper.RoleMapper;
import com.xichuan.server.resp.RoleResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;
    public List<RoleResp> all() {
        RoleExample roleExample = new RoleExample();
//        roleExample.createCriteria().andIdEqualTo("1");
//        roleExample.setOrderByClause("id desc");
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        List<RoleResp> roleRespList =new ArrayList<RoleResp>() ;
        for(int i = 0;i<roleList.size();i++){
            Role role = roleList.get(i);
            RoleResp roleResp = new RoleResp();
            BeanUtils.copyProperties(role,roleResp);
            roleRespList.add(roleResp);
        }
        return roleRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        RoleExample roleExample = new RoleExample();
//        roleExample.createCriteria().andIdEqualTo("1");
//        roleExample.setOrderByClause("id desc");
        List<Role> roleList = roleMapper.selectByExample(roleExample);//写在select的下一行
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageReq.setTotal(pageInfo.getTotal());
        List<RoleResp> roleDtoList = CopyUtil.copyList(roleList, RoleResp.class);
        pageReq.setList(roleDtoList);
    }
    public void save(RoleReq roleReq) {
        Role role = CopyUtil.copy(roleReq, Role.class);
        if(StringUtils.isEmpty(roleReq.getId())){
            this.insert(role);
        }else{
            this.update(role);
        }
    }
    public void insert(Role role) {
        role.setId(IdUtil.simpleUUID());
        roleMapper.insert(role);
    }
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey( id);
    }

}
