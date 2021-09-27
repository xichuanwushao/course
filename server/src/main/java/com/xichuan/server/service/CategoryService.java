package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.CategoryReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.Category;
import com.xichuan.server.domain.CategoryExample;
import com.xichuan.server.mapper.CategoryMapper;
import com.xichuan.server.resp.CategoryResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    public List<CategoryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
//        categoryExample.createCriteria().andIdEqualTo("1");
//        categoryExample.setOrderByClause("id desc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryResp> categoryRespList =new ArrayList<CategoryResp>() ;
        for(int i = 0;i<categoryList.size();i++){
            Category category = categoryList.get(i);
            CategoryResp categoryResp = new CategoryResp();
            BeanUtils.copyProperties(category,categoryResp);
            categoryRespList.add(categoryResp);
        }
        return categoryRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        CategoryExample categoryExample = new CategoryExample();
//        categoryExample.createCriteria().andIdEqualTo("1");
//        categoryExample.setOrderByClause("id desc");
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);//写在select的下一行
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageReq.setTotal(pageInfo.getTotal());
        List<CategoryResp> categoryDtoList = CopyUtil.copyList(categoryList, CategoryResp.class);
        pageReq.setList(categoryDtoList);
    }
    public void save(CategoryReq categoryReq) {
        Category category = CopyUtil.copy(categoryReq, Category.class);
        if(StringUtils.isEmpty(categoryReq.getId())){
            this.insert(category);
        }else{
            this.update(category);
        }
    }
    public void insert(Category category) {
        category.setId(IdUtil.simpleUUID());
        categoryMapper.insert(category);
    }
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
    public void delete(String id) {
        categoryMapper.deleteByPrimaryKey( id);
    }

}
