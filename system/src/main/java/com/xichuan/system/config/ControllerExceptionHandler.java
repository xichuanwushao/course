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
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
//只要代码抛异常 并且异常是ValidatorException 就会被拦截掉
@ExceptionHandler(value = ValidatorException.class)
@ResponseBody
public CommonResp validatorExceptionHandler(ValidatorException e) {
    CommonResp responseDto = new CommonResp();
    responseDto.setSuccess(false);
    logger.warn(e.getMessage());
    responseDto.setMessage("请求参数异常！");
    return responseDto;
}

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp businessExceptionHandler(BusinessException e) {
        CommonResp responseDto = new CommonResp();
        responseDto.setSuccess(false);
        logger.error("业务异常：{}", e.getCode().getDesc());
        responseDto.setMessage(e.getCode().getDesc());
        return responseDto;
    }
}
