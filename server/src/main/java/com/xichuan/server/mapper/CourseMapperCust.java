package com.xichuan.server.mapper;

import com.xichuan.server.req.CoursePageReq;
import com.xichuan.server.req.CourseReq;
import com.xichuan.server.req.SortReq;
import com.xichuan.server.resp.CourseResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapperCust {
    int updateTime(@Param("courseId")String courseId);

    int updateSort(SortReq sortDto);

    int moveSortsBackward(SortReq sortDto);

    int moveSortsForward(SortReq sortDto);

    List<CourseResp> list(@Param("coursePageReq") CoursePageReq coursePageReq);
}
