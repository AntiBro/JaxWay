package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author huaili
 * @Date 2019/5/14 16:50
 * @Description BaseController
 **/
public class BaseController {
    Logger log = LoggerFactory.getLogger(getClass());


    /**
     * handler error
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultVO handle(Exception e) {
        return ResultVO.error(e.getMessage());
    }
}
