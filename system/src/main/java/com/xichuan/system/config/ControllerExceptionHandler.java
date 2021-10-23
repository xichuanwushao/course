package com.xichuan.system.config;

import com.xichuan.server.exception.BusinessException;
import com.xichuan.server.exception.ValidatorException;
import com.xichuan.server.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
//只要代码抛异常 并且异常是ValidatorException 就会被拦截掉
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp businessExceptionHandler(BusinessException e) {
        CommonResp commonResp = new CommonResp();
        commonResp.setSuccess(false);
        LOG.error("业务异常:{}"+e.getCode().getDesc());
        commonResp.setMessage(e.getCode().getDesc());
        return commonResp;
    }
}
