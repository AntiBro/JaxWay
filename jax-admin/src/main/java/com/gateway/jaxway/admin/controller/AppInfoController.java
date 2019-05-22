package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.service.AppInfoService;
import com.gateway.jaxway.admin.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @Author huaili
 * @Date 2019/4/24 20:39
 * @Description AppInfoController
 **/
@RestController
public class AppInfoController extends BaseController{

    @Autowired
    private AppInfoService appInfoService;

    /**
     *  get the jax-way client authority information
     * @param jaxId ${jaxway.server.id}
     * @param appId ${jaxway.appid}
     * @param versionId
     * @param exchange
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/client/getAppInfo")
    public ResultVO getClientAppAuthorityInfo(String jaxId,String appId,Integer versionId, ServerWebExchange exchange)  {
        log.info("getClientAppAuthorityInfo appid={} remoteIP={} versionId={}",appId, IpUtil.getIpAddr(exchange.getRequest()),versionId);
        return ResultVO.success(appInfoService.getJaxClientAuthentication(jaxId,appId,versionId));
    }

    /**
     * get the jax-way server app authority information
     * @param id
     * @param versionId
     * @param exchange
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/server/getAppInfo")
    public ResultVO getServerAppAuthorityInfo(String id,Integer versionId, ServerWebExchange exchange) throws UnsupportedEncodingException {
        log.info("getServerAppAuthorityInfo appid={} remoteIP={} versionId={}",id, IpUtil.getIpAddr(exchange.getRequest()),versionId);
        return ResultVO.success(appInfoService.getJaxServerAuthentication(id,versionId));
    }

    /**
     * get the white list for jax-way server through the serverId(${jaxway.server.id})
     * @param id jax-way serverId
     * @param versionId
     * @param exchange
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/server/getWhiteList")
    public ResultVO getServerWhiteListInfo(String id,Integer versionId, ServerWebExchange exchange) throws UnsupportedEncodingException {
        log.info("getServerWhiteListInfo appid={} remoteIP={} versionId={}",id, IpUtil.getIpAddr(exchange.getRequest()),versionId);
        return ResultVO.success(appInfoService.getServerWhiteListInfo(id,versionId));
    }


    /**
     * get the route definition for jax-server through the serverId(${jaxway.server.id})
     * @param id
     * @param versionId
     * @param exchange
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping("/server/getRouteInfo")
    public ResultVO getRouteInfo(String id,Integer versionId, ServerWebExchange exchange) throws URISyntaxException {
        log.info("getRouteInfo appid={} remoteIP={} versionId={}",id, IpUtil.getIpAddr(exchange.getRequest()),versionId);
        return ResultVO.success(appInfoService.getJaxRouteDefinitions(id,versionId));
    }
}
