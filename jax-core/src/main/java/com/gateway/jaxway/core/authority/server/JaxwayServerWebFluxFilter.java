package com.gateway.jaxway.core.authority.server;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.gateway.jaxway.core.common.JaxwayConstant.JAXWAY_APP_ID;

/**
 * @Author huaili
 * @Date 2019/4/26 11:26
 * @Description JaxwayServerWebFluxFilter for JaxWay Server
 **/
public class JaxwayServerWebFluxFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();

       // String appId = request.getHeaders().getFirst(JAXWAY_APP_ID);
        String uri = request.getURI().getPath();

        return null;
    }
}
