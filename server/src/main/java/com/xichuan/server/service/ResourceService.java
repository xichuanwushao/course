package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.ResourceReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Resource;
import com.xichuan.server.domain.ResourceExample;
import com.xichuan.server.mapper.ResourceMapper;
import com.xichuan.server.resp.ResourceResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class ResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    public List<ResourceResp> all() {
        ResourceExample resourceExample = new ResourceExample();
//        resourceExample.createCriteria().andIdEqualTo("1");
//        resourceExample.setOrderByClause("id desc");
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
        List<ResourceResp> resourceRespList =new ArrayList<ResourceResp>() ;
        for(int i = 0;i<resourceList.size();i++){
            Resource resource = resourceList.get(i);
            ResourceResp resourceResp = new ResourceResp();
            BeanUtils.copyProperties(resource,resourceResp);
            resourceRespList.add(resourceResp);
        }
        return resourceRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        ResourceExample resourceExample = new ResourceExample();
//        resourceExample.createCriteria().andIdEqualTo("1");
//        resourceExample.setOrderByClause("id desc");
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);//写在select的下一行
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageReq.setTotal(pageInfo.getTotal());
        List<ResourceResp> resourceDtoList = CopyUtil.copyList(resourceList, ResourceResp.class);
        pageReq.setList(resourceDtoList);
    }
    public void save(ResourceReq resourceReq) {
        Resource resource = CopyUtil.copy(resourceReq, Resource.class);
        if(StringUtils.isEmpty(resourceReq.getId())){
            this.insert(resource);
        }else{
            this.update(resource);
        }
    }
    public void insert(Resource resource) {
        resource.setId(IdUtil.simpleUUID());
        resourceMapper.insert(resource);
    }
    public void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey( id);
    }

}
