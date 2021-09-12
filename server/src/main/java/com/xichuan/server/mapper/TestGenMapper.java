package com.xichuan.server.mapper;

import com.xichuan.server.domain.TestGen;
import com.xichuan.server.domain.TestGenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestGenMapper {
    long countByExample(TestGenExample example);

    int deleteByExample(TestGenExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestGen record);

    int insertSelective(TestGen record);

    List<TestGen> selectByExample(TestGenExample example);

    TestGen selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestGen record, @Param("example") TestGenExample example);

    int updateByExample(@Param("record") TestGen record, @Param("example") TestGenExample example);

    int updateByPrimaryKeySelective(TestGen record);

    int updateByPrimaryKey(TestGen record);
}