package com.xichuan.server.req;


import java.util.HashSet;
import java.util.List;

public class LoginUserReq {
    private String id;

    private String loginName;

    private String name;

    /***
     * 登录标识
     * @return
     */
    private String token;

    /***
     * 所以资源 用于前端界面控制
     * @return
     */
    private List<ResourceReq> resources;

    /***
     * 所有资源中的请求 用于后端接口拦截 用set过滤重复的接口
     */
    private HashSet<String> requests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResourceReq> getResources() {
        return resources;
    }

    public void setResources(List<ResourceReq> resources) {
        this.resources = resources;
    }

    public HashSet<String> getRequests() {
        return requests;
    }

    public void setRequests(HashSet<String> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "LoginUserResp{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", resources=" + resources +
                ", requests=" + requests +
                '}';
    }
}