package com.xichuan.business.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xichuan.server.domain.Category;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.req.CategoryReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.CategoryResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CategoryService;
import com.xichuan.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@Controller 如果接口返回页面用Controller


@RequestMapping("category")
@RestController//@Controller 如果接口返回Json 用RestController
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    public static final String BUSINESS_NAME="分类";
    @RequestMapping("test")
    public String category(){
        return "success";
    }

    @RequestMapping("all")
    public List<CategoryResp> all(){
        return categoryService.all();
    }

    @PostMapping("listPage")
    public CommonResp listPage(@RequestBody PageReq pageReq){
        CommonResp commonResp = new CommonResp();
        categoryService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody CategoryReq categoryReq){
        // TODO 保存校验
        // 保存校验
        ValidatorUtil.require(categoryReq.getParent(), "父id");
        ValidatorUtil.require(categoryReq.getName(), "名称");
        ValidatorUtil.length(categoryReq.getName(), "名称", 1, 50);

        CommonResp commonResp = new CommonResp();
        categoryService.save(categoryReq);
        commonResp.setContent(categoryReq);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp commonResp = new CommonResp();
        categoryService.delete(id);
        return commonResp;
    }
}
