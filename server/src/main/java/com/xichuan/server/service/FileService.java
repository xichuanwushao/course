package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.req.FileReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.File;
import com.xichuan.server.domain.FileExample;
import com.xichuan.server.mapper.FileMapper;
import com.xichuan.server.resp.FileResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
        import java.util.Date;
@Service
public class FileService {
    @Resource
    private FileMapper fileMapper;
    public List<FileResp> all() {
        FileExample fileExample = new FileExample();
//        fileExample.createCriteria().andIdEqualTo("1");
//        fileExample.setOrderByClause("id desc");
        List<File> fileList = fileMapper.selectByExample(fileExample);
        List<FileResp> fileRespList =new ArrayList<FileResp>() ;
        for(int i = 0;i<fileList.size();i++){
            File file = fileList.get(i);
            FileResp fileResp = new FileResp();
            BeanUtils.copyProperties(file,fileResp);
            fileRespList.add(fileResp);
        }
        return fileRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        FileExample fileExample = new FileExample();
//        fileExample.createCriteria().andIdEqualTo("1");
//        fileExample.setOrderByClause("id desc");
        List<File> fileList = fileMapper.selectByExample(fileExample);//写在select的下一行
        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageReq.setTotal(pageInfo.getTotal());
        List<FileResp> fileDtoList = CopyUtil.copyList(fileList, FileResp.class);
        pageReq.setList(fileDtoList);
    }
    public void save(FileReq fileReq) {
        File file = CopyUtil.copy(fileReq, File.class);
        File fileDb = selectByKey(fileReq.getKey());
        if(fileDb==null){
            this.insert(file);
        }else{
            fileDb.setShardIndex(fileReq.getShardIndex());
            this.update(fileDb);
        }
    }
    public void insert(File file) {
        Date now = new Date();
        file.setId(IdUtil.simpleUUID());
        fileMapper.insert(file);
    }
    public void update(File file) {
        fileMapper.updateByPrimaryKey(file);
    }
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey( id);
    }

    public File selectByKey(String key){
        FileExample example = new FileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<File> fileList = fileMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(fileList)){
            return null;
        }else {
            return fileList.get(0);
        }
    }

    /***
     * 根据文件标识查询数据库记录
     * @param key
     * @return
     */
    public FileResp findByKey(String key) {
        return CopyUtil.copy(selectByKey(key),FileResp.class);
    }
}
