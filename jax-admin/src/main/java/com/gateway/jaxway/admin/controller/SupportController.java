package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.vo.GatewayVO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huaili
 * @Date 2019/5/20 14:43
 * @Description SupportController
 **/
@RestController
@RequestMapping("/portal/support")
@CrossOrigin
public class SupportController extends BaseController {



    @RequestMapping("/getPredicatesInfo")
    public ResultVO getPredicatesFactoryInfos(){
        return ResultVO.success(GatewayVO.getAllPredicatesInfo());
    }


    @RequestMapping("/getFilterInfos")
    public ResultVO getFilterInfos(){
        return ResultVO.success(GatewayVO.getAllFilterInfo());
    }
}
