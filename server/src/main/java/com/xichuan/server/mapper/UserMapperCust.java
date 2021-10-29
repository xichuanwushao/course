package com.xichuan.server.mapper;


import com.xichuan.server.req.ResourceReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperCust {
    List<ResourceReq> findResources(@Param("userId")String userId);
}
