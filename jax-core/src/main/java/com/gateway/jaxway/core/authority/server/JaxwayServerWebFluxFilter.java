package com.gateway.jaxway.core.authority.server;

import com.alibaba.fastjson.JSON;
import com.gateway.common.JaxwayCoder;
import com.gateway.common.JaxwayServerAuthenticationDataStore;
import com.gateway.common.JaxwayWhiteList;
import com.gateway.common.beans.ResultVO;
import com.gateway.common.defaults.Base64JaxwayCoder;
import com.gateway.common.defaults.LocalJaxwayAuthenticationServerDataStore;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.gateway.common.JaxwayConstant.*;

/**
 * @Author huaili
 * @Date 2019/4/26 11:26
 * @Description JaxwayServerWebFluxFilter for JaxWay Server
 **/
public class JaxwayServerWebFluxFilter implements WebFilter {

    private JaxwayWhiteList jaxwayWhiteList;

    private JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore;

    private JaxwayCoder jaxwayCoder;

    private Log log;

    public JaxwayServerWebFluxFilter(JaxwayWhiteList jaxwayWhiteList, Log log, JaxwayCoder jaxwayCoder, JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore){
        this.jaxwayWhiteList = jaxwayWhiteList;
        this.log = log;
        this.jaxwayCoder = jaxwayCoder;
        this.jaxwayServerAuthenticationDataStore = jaxwayServerAuthenticationDataStore;
    }
    public JaxwayServerWebFluxFilter(){
        this(LocalJaxwayWhiteList.create(),new DefaultLogImpl(JaxwayServerWebFluxFilter.class),new Base64JaxwayCoder(), LocalJaxwayAuthenticationServerDataStore.create());
    }


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();

        String uri = request.getURI().getPath();
        // check if request is in whitelist,so let it go through jaxway
        if(jaxwayWhiteList.match(uri)){
            // white list request no need to check jax-way-app-id
            return webFilterChain.filter(serverWebExchange);
        }

        String appId = request.getHeaders().getFirst(JAXWAY_APP_ID);

        if(jaxwayServerAuthenticationDataStore.match(appId,uri)){
            try {
                // add jax-way-url and jax-way-token in Http Headers to validate the request on client side
                //向headers中放文件，记得build
                ServerHttpRequest wrapRequest = request.mutate().header(JAXWAY_SERVER_ID, jaxwayCoder.encode(appId)).header(JAXWAY_REQUEST_TOKEN_HEADER_KEY,jaxwayCoder.encode(jaxwayServerAuthenticationDataStore.getRegxOfUri(appId,uri))).build();
                //将现在的request 变成 change对象
                ServerWebExchange wrapserverWebExchange = serverWebExchange.mutate().request(wrapRequest).build();

                log.log("jaxway legal webflux request ip="+request.getRemoteAddress()+" uri="+request.getURI().getPath()+" appId="+appId);
                return webFilterChain.filter(wrapserverWebExchange);
            } catch (Exception e) {
                e.printStackTrace();
                log.log(Log.LogType.ERROR,e.getMessage());
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return  response.writeWith(Flux.empty());

            }
        }

        log.log(Log.LogType.WARN,"jaxway server found illegal webflux request ip="+request.getRemoteAddress()+" uri="+request.getURI().getPath()+" appId="+appId);

        DataBuffer wrap = serverWebExchange.getResponse().bufferFactory().wrap(JSON.toJSONString(ResultVO.NOT_AUTHORIZED_REQUEST).getBytes());
        return  response.writeWith(Flux.just(wrap));
    }

}
