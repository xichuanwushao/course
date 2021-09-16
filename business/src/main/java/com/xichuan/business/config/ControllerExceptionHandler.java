package com.xichuan.business.config;

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
    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public CommonResp validatorExceptionHandler(ValidatorException e) {
        CommonResp responseDto = new CommonResp();
        responseDto.setSuccess(false);
        LOG.warn(e.getMessage());responseDto.setMessage("后台效验异常："+e.getMessage());
        return responseDto;
    }
}
