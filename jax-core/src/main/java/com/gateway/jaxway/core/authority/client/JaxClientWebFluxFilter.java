package com.gateway.jaxway.core.authority.client;

import com.alibaba.fastjson.JSON;
import com.gateway.common.JaxwayClientValidator;
import com.gateway.common.beans.JaxRequest;
import com.gateway.common.beans.ResultVO;
import com.gateway.common.defaults.Base64JaxwayCoder;
import com.gateway.common.defaults.DefaultJaxwayClientValidator;
import com.gateway.common.defaults.LocalJaxwayAuthenticationClientDataStore;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

import static com.gateway.common.JaxwayConstant.JAXWAY_REQUEST_TOKEN_HEADER_KEY;
import static com.gateway.common.JaxwayConstant.JAXWAY_SERVER_APP_ID;

/**
 * @Author huaili
 * @Date 2019/4/21 18:49
 * @Description JaxClientWebFluxFilter
 **/
public class JaxClientWebFluxFilter implements WebFilter {
    private Log log;
    private JaxwayClientValidator jaxwayClientValidator;

    public JaxClientWebFluxFilter(Log log){
        this.log = log;
        this.jaxwayClientValidator = new DefaultJaxwayClientValidator(new Base64JaxwayCoder(), LocalJaxwayAuthenticationClientDataStore.instance());
    }

    public JaxClientWebFluxFilter(){
        this(new DefaultLogImpl(JaxClientWebFluxFilter.class));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();

        JaxRequest jaxRequest = JaxRequest.newBuilder().appId(request.getHeaders().getFirst(JAXWAY_SERVER_APP_ID)).token(request.getHeaders().getFirst(JAXWAY_REQUEST_TOKEN_HEADER_KEY)).build();
        try {
            if(jaxwayClientValidator.validate(jaxRequest)){
                log.log("legal webflux request jaxRequest={}",JSON.toJSONString(jaxRequest));
                return webFilterChain.filter(serverWebExchange);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.log(Log.LogType.WARN,"found illegal webflux request ip="+request.getRemoteAddress()+" uri="+request.getURI().getPath());

        DataBuffer wrap = serverWebExchange.getResponse().bufferFactory().wrap(JSON.toJSONString(ResultVO.NOT_AUTHORIZED_REQUEST).getBytes());
        return  response.writeWith(Flux.just(wrap));
    }
}
