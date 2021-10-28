package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.RoleResourceReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.RoleResource;
import com.xichuan.server.domain.RoleResourceExample;
import com.xichuan.server.mapper.RoleResourceMapper;
import com.xichuan.server.resp.RoleResourceResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class RoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;
    public List<RoleResourceResp> all() {
        RoleResourceExample roleResourceExample = new RoleResourceExample();
//        roleResourceExample.createCriteria().andIdEqualTo("1");
//        roleResourceExample.setOrderByClause("id desc");
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);
        List<RoleResourceResp> roleResourceRespList =new ArrayList<RoleResourceResp>() ;
        for(int i = 0;i<roleResourceList.size();i++){
            RoleResource roleResource = roleResourceList.get(i);
            RoleResourceResp roleResourceResp = new RoleResourceResp();
            BeanUtils.copyProperties(roleResource,roleResourceResp);
            roleResourceRespList.add(roleResourceResp);
        }
        return roleResourceRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        RoleResourceExample roleResourceExample = new RoleResourceExample();
//        roleResourceExample.createCriteria().andIdEqualTo("1");
//        roleResourceExample.setOrderByClause("id desc");
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);//写在select的下一行
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageReq.setTotal(pageInfo.getTotal());
        List<RoleResourceResp> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RoleResourceResp.class);
        pageReq.setList(roleResourceDtoList);
    }
    public void save(RoleResourceReq roleResourceReq) {
        RoleResource roleResource = CopyUtil.copy(roleResourceReq, RoleResource.class);
        if(StringUtils.isEmpty(roleResourceReq.getId())){
            this.insert(roleResource);
        }else{
            this.update(roleResource);
        }
    }
    public void insert(RoleResource roleResource) {
        roleResource.setId(IdUtil.simpleUUID());
        roleResourceMapper.insert(roleResource);
    }
    public void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey( id);
    }

}
