package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.exception.BusinessException;
import com.xichuan.server.exception.BusinessExceptionCode;
import com.xichuan.server.req.UserReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.User;
import com.xichuan.server.domain.UserExample;
import com.xichuan.server.mapper.UserMapper;
import com.xichuan.server.resp.UserResp;
import com.xichuan.server.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public List<UserResp> all() {
        UserExample userExample = new UserExample();
//        userExample.createCriteria().andIdEqualTo("1");
//        userExample.setOrderByClause("id desc");
        List<User> userList = userMapper.selectByExample(userExample);
        List<UserResp> userRespList =new ArrayList<UserResp>() ;
        for(int i = 0;i<userList.size();i++){
            User user = userList.get(i);
            UserResp userResp = new UserResp();
            BeanUtils.copyProperties(user,userResp);
            userRespList.add(userResp);
        }
        return userRespList;
    }
    public void listPage(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());//对第一个select有用
        UserExample userExample = new UserExample();
//        userExample.createCriteria().andIdEqualTo("1");
//        userExample.setOrderByClause("id desc");
        List<User> userList = userMapper.selectByExample(userExample);//写在select的下一行
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageReq.setTotal(pageInfo.getTotal());
        List<UserResp> userDtoList = CopyUtil.copyList(userList, UserResp.class);
        pageReq.setList(userDtoList);
    }
    public void save(UserReq userReq) {
        User user = CopyUtil.copy(userReq, User.class);
        if(StringUtils.isEmpty(userReq.getId())){
            this.insert(user);
        }else{
            this.update(user);
        }
    }
    public void insert(User user) {
        user.setId(IdUtil.simpleUUID());
        User userDb = this.selectByLoginName(user.getLoginName());
        if(userDb != null ){
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        userMapper.insert(user);
    }
    public void update(User user) {
        user.setPassword(null);
        userMapper.updateByPrimaryKeySelective(user);
    }
    public void delete(String id) {
        userMapper.deleteByPrimaryKey( id);
    }

    /***
     * 根据用户名查询用户信息
     * @param loginName
     * @return
     */
    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }else {
            return userList.get(0);
        }
    }
    /***
     * 重置密码
     * @param userReq
     * @return
     */
    public void savePassword(UserReq userReq){
        User user = new User();
        user.setId(userReq.getId());
        user.setPassword(userReq.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }
}
