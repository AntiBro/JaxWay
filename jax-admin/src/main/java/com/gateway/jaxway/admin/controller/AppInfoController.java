package com.gateway.jaxway.admin.controller;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.common.beans.OpType;
import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.beans.JaxRouteDefinition;
import com.gateway.jaxway.admin.util.IpUtil;
import com.gateway.jaxway.admin.util.RouteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @Author huaili
 * @Date 2019/4/24 20:39
 * @Description AppInfoController
 **/
@RestController
public class AppInfoController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JaxwayCoder jaxwayCoder;

    @RequestMapping("/client/getAppInfo")
    public ResultVO getClientAppAuthorityInfo(String appid,Long versionId, ServerWebExchange exchange) throws UnsupportedEncodingException {


        log.info("getClientAppAuthorityInfo appid={} remoteIP={} versionId={}",appid, IpUtil.getIpAddr(exchange.getRequest()),versionId);


        JaxClientAuthentication jaxAuthentication = new JaxClientAuthentication();
        String appId = "test";
        jaxAuthentication.setOpType(OpType.ADD_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode(appId));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");
        jaxAuthentication.setToken(jaxwayCoder.encode("/sohu/**"));
        jaxAuthentication.setVersionId(1);

        if(appid.equals("test2")){
            return ResultVO.success(JaxClientAuthentication.NONE);
        }
        return ResultVO.success(jaxAuthentication);
    }

    @RequestMapping("/server/getAppInfo")
    public ResultVO getServerAppAuthorityInfo(String id,Long versionId, ServerWebExchange exchange) throws UnsupportedEncodingException {


        log.info("getServerAppAuthorityInfo appid={} remoteIP={} versionId={}",id, IpUtil.getIpAddr(exchange.getRequest()),versionId);


        JaxServerAuthentication jaxAuthentication = new JaxServerAuthentication();
        Set<String> uriSets = new HashSet<>();

        String appId = "test";
        uriSets.add(jaxwayCoder.decode("/sohu/**"));

        jaxAuthentication.setUriRegxSet(uriSets);
        jaxAuthentication.setOpType(OpType.ADD_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode(appId));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");
        jaxAuthentication.setVersionId(1);


        if(id.equals("test2")){
            return ResultVO.success(JaxClientAuthentication.NONE);
        }
        return ResultVO.success(jaxAuthentication);
    }

    @RequestMapping("/server/getWhiteList")
    public ResultVO getServerWhiteListInfo(String id,Long versionId, ServerWebExchange exchange) throws UnsupportedEncodingException {


        log.info("getServerWhiteListInfo appid={} remoteIP={} versionId={}",id, IpUtil.getIpAddr(exchange.getRequest()),versionId);


        JaxServerAuthentication jaxAuthentication = new JaxServerAuthentication();
        Set<String> uriSets = new HashSet<>();

        String appId = "test";
        uriSets.add(jaxwayCoder.encode("/sogou/**"));
        uriSets.add(jaxwayCoder.encode("/sohu/**"));
        uriSets.add(jaxwayCoder.encode("/sohu"));

        jaxAuthentication.setUriRegxSet(uriSets);
        jaxAuthentication.setOpType(OpType.ADD_WHITE_SERVER_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode(appId));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");
        jaxAuthentication.setVersionId(1);


        return ResultVO.success(jaxAuthentication);
    }


    @RequestMapping("/server/getRouteInfo")
    public ResultVO getRouteInfo(String id,Long versionId, ServerWebExchange exchange) throws URISyntaxException {


        log.info("getRouteInfo appid={} remoteIP={} versionId={}",id, IpUtil.getIpAddr(exchange.getRequest()),versionId);

        List<JaxRouteDefinition> jaxRouteDefinitions = new ArrayList<>();
        JaxRouteDefinition jaxRouteDefinition = new JaxRouteDefinition();
        jaxRouteDefinition.setVersionId(1);
        jaxRouteDefinition.setOpType(OpType.ADD_ROUTE);

        BeanUtils.copyProperties(RouteUtil.generatePathRouteDefition("http://m.sohu.com","/sohu,/sohu/**"),jaxRouteDefinition);

        jaxRouteDefinitions.add(jaxRouteDefinition);
//        if(id.equals("test2")){
//            return ResultVO.success(JaxClientAuthentication.NONE);
//        }
        return ResultVO.success(jaxRouteDefinitions);
    }
}
