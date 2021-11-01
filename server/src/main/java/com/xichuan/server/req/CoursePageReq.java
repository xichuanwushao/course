package com.xichuan.server.req;


public class CoursePageReq extends PageReq{

    private String status;
    private String categoryId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CoursePageReq{" +
                "status='" + status + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                "} " + super.toString();
    }
}