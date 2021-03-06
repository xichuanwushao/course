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
import org.springframework.transaction.annotation.Transactional;
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
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryResp> categoryRespList = CopyUtil.copyList(categoryList,CategoryResp.class);
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
    /**
     * 删除
     */
    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除子分类
     * @param id
     */
    public void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("0".equals(category.getParent())) {
            // 如果是一级分类，需要删除其下的二级分类
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(example);
        }
    }
}
