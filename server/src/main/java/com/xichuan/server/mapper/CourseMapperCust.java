package com.xichuan.server.mapper;

import com.xichuan.server.req.SortReq;
import org.apache.ibatis.annotations.Param;

public interface CourseMapperCust {
    int updateTime(@Param("courseId")String courseId);

    int updateSort(SortReq sortDto);

    int moveSortsBackward(SortReq sortDto);

    int moveSortsForward(SortReq sortDto);
}
