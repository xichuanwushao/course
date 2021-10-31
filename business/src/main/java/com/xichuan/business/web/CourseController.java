package com.xichuan.business.web;

import com.xichuan.server.enums.CourseStatusEnum;
import com.xichuan.server.req.CourseReq;
import com.xichuan.server.req.PageReq;
import com.xichuan.server.resp.CommonResp;
import com.xichuan.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("webCourseController")
@RequestMapping("/web/course")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    /**
     * 列表查询，查询最新的3门已发布的课程
     */
    @GetMapping("/list-new")
    public CommonResp listNew() {
        PageReq pageReq = new PageReq();
        pageReq.setPage(1);
        pageReq.setSize(9);
        CommonResp responseDto = new CommonResp();
        List<CourseReq> courseDtoList = courseService.listNew(pageReq);
        responseDto.setContent(courseDtoList);
        return responseDto;
    }


    /**
     * 列表查询
     */
    @PostMapping("/list")
    public CommonResp list(@RequestBody PageReq pageReq) {
        CommonResp commonResp = new CommonResp();
        courseService.listPage(pageReq);
        commonResp.setContent(pageReq);
        return commonResp;
    }


}
