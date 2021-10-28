package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.domain.RoleResource;
import com.xichuan.server.domain.RoleResourceExample;
import com.xichuan.server.mapper.RoleResourceMapper;
import com.xichuan.server.req.RoleReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Role;
import com.xichuan.server.domain.RoleExample;
import com.xichuan.server.mapper.RoleMapper;
import com.xichuan.server.resp.RoleResp;
import com.xichuan.server.util.CopyUtil;
import com.xichuan.server.util.UuidUtil;
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

    @Resource
    private RoleResourceMapper roleResourceMapper;

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
    /**
     * 按角色保存资源
     */
    public void saveResource(RoleReq roleReq) {
        String roleId = roleReq.getId();
        List<String> resourceIds = roleReq.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }


    /**
     * 按角色加载资源
     * @param roleId
     */
    public List<String> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }
}
