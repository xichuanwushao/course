package com.xichuan.server.mapper;

import org.apache.ibatis.annotations.Param;

public interface CourseMapperCust {
    int updateTime(@Param("courseId")String courseId);
}
