package com.xichuan.server.req;

import java.math.BigDecimal;
import java.util.Date;

public class CoursePageReq extends PageReq{

    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CoursePageReq{" +
                "status='" + status + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                "} " + super.toString();
    }
}