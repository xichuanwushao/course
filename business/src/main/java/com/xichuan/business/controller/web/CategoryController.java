package com.xichuan.business.controller.web;

import com.xichuan.server.resp.CategoryResp;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("webCategoryController")
@RequestMapping("/web/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryService categoryService;

    /**
     * 列表查询
     */
    @PostMapping("/all")
    public CommonResp all() {
        CommonResp commonResp = new CommonResp();
        List<CategoryResp> categoryDtoList = categoryService.all();
        commonResp.setContent(categoryDtoList);
        return commonResp;
    }
}
