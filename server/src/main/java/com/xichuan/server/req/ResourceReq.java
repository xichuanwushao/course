package com.xichuan.server.req;


import java.util.List;

public class ResourceReq {

    /**
     * id
     */
    private String id;

    /**
     * 名称|菜单或路由
     */
    private String name;

    /**
     * 页面|路由
     */
    private String page;

    /**
     * 请求|接口
     */
    private String request;

    /**
     * 父id
     */
    private String parent;

    private List<ResourceReq> children;


    public List<ResourceReq> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceReq> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", page=").append(page);
        sb.append(", request=").append(request);
        sb.append(", parent=").append(parent);
        sb.append("]");
        return sb.toString();
    }

}