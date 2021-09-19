package com.xichuan.${module}.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.${Domain};
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.${Domain}Req;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.${Domain}Resp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.${Domain}Service;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("${domain}")
@RestController//@Controller 如果接口返回Json 用RestController
public class ${Domain}Controller {
    @Resource
    private ${Domain}Service ${domain}Service;

    public static final String BUSINESS_NAME="${tableNameCn}";
    @RequestMapping("test")
    public String ${domain}(){
        return "success";
    }

    @RequestMapping("all")
    public List<${Domain}Resp> all(){
        return ${domain}Service.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        ${domain}Service.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody ${Domain}Req ${domain}Req){
        // TODO 保存校验
        // 保存校验
        <#list fieldList as field>
            <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                <#if !field.nullAble>
        ValidatorUtil.require(${domain}Req.get${field.nameBigHump}(), "${field.nameCn}");
                </#if>
                <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Req.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
                </#if>
            </#if>
        </#list>

        CommonResp commonResp = new CommonResp();
        ${domain}Service.save(${domain}Req);
        commonResp.setContent(${domain}Req);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        ${domain}Service.delete(id);
        return commonResp;
    }
}
