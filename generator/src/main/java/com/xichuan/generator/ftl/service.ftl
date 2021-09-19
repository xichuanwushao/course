package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.${Domain}Req;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.${Domain};
import com.xichuan.server.domain.${Domain}Example;
import com.xichuan.server.mapper.${Domain}Mapper;
import com.xichuan.server.resp.${Domain}Resp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
<#list typeSet as type>
    <#if type=='Date'>
        import java.util.Date;
    </#if>
</#list>
@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;
    public List<${Domain}Resp> all() {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
//        ${domain}Example.createCriteria().andIdEqualTo("1");
//        ${domain}Example.setOrderByClause("id desc");
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        List<${Domain}Resp> ${domain}RespList =new ArrayList<${Domain}Resp>() ;
        for(int i = 0;i<${domain}List.size();i++){
            ${Domain} ${domain} = ${domain}List.get(i);
            ${Domain}Resp ${domain}Resp = new ${Domain}Resp();
            BeanUtils.copyProperties(${domain},${domain}Resp);
            ${domain}RespList.add(${domain}Resp);
        }
        return ${domain}RespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        ${Domain}Example ${domain}Example = new ${Domain}Example();
//        ${domain}Example.createCriteria().andIdEqualTo("1");
//        ${domain}Example.setOrderByClause("id desc");
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
        ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);//写在select的下一行
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageReq.setTotal(pageInfo.getTotal());
        List<${Domain}Resp> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Resp.class);
        pageReq.setList(${domain}DtoList);
    }
    public void save(${Domain}Req ${domain}Req) {
        ${Domain} ${domain} = CopyUtil.copy(${domain}Req, ${Domain}.class);
        if(StringUtils.isEmpty(${domain}Req.getId())){
            this.insert(${domain});
        }else{
            this.update(${domain});
        }
    }
    public void insert(${Domain} ${domain}) {
        <#list typeSet as type>
            <#if type=='Date'>
        Date now = new Date();
            </#if>
        </#list>
        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
        ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(IdUtil.simpleUUID());
        ${domain}Mapper.insert(${domain});
    }
    public void update(${Domain} ${domain}) {
        <#list fieldList as field>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey( id);
    }

}
