package com.xichuan.server.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xichuan.server.exception.BusinessException;
import com.xichuan.server.exception.BusinessExceptionCode;
import com.xichuan.server.mapper.UserMapperCust;
import com.xichuan.server.req.LoginUserReq;
import com.xichuan.server.req.ResourceReq;
import com.xichuan.server.req.UserReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.domain.User;
import com.xichuan.server.domain.UserExample;
import com.xichuan.server.mapper.UserMapper;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.resp.LoginUserResp;
import com.xichuan.server.resp.UserResp;
import com.xichuan.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMapperCust userMapperCust;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
        //cd23f8c494e2339e5cc679ba60bfc409是空字符串
        if("cd23f8c494e2339e5cc679ba60bfc409".equals(userReq.getPassword())){
            throw new BusinessException(BusinessExceptionCode.USER_PASSWORD_NOT_EXIST);
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    /***
     * 登录
     * @param userReq
     * @return
     */
    public LoginUserResp login(UserReq userReq){
        User dbUser = selectByLoginName(userReq.getLoginName());
        if(dbUser == null){
            //用户名不存在
            logger.info("用户名不存在:{}",userReq.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else{
            if(dbUser.getPassword().equals(userReq.getPassword())){
                //登录成功
                LoginUserReq  loginUserReq = CopyUtil.copy(dbUser, LoginUserReq.class);
                setAuth(loginUserReq);
                return CopyUtil.copy(loginUserReq,LoginUserResp.class);
            }else {
                //密码不对
                logger.info("密码不对,输入密码:{},数据库密码:{}",userReq.getPassword(),dbUser.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);

            }
        }
    }




    /**
     * 为登录用户读取权限
     */
    private void setAuth(LoginUserReq loginUserReq) {
        List<ResourceReq> resourceReqs = userMapperCust.findResources(loginUserReq.getId());
        loginUserReq.setResources(resourceReqs);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceReqs)) {
            for (int i = 0, l = resourceReqs.size(); i < l; i++) {
                ResourceReq resourceReq = resourceReqs.get(i);
                String arrayString = resourceReq.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
        logger.info("有权限的请求：{}", requestSet);
        loginUserReq.setRequests(requestSet);
    }
}
