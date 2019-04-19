package com.gateway.jaxway.test.config;


import com.alibaba.fastjson.JSON;
import com.gateway.jaxway.core.vo.ResultVO;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Author huaili
 * @Date 2019/4/10 20:41
 * @Description webFlux 测试的认证过滤器
 **/
@Component
@Order(-1)
public class AuthorWebFluxFilter implements WebFilter {
    public static byte[] UNAUTHORIZED_INFO = (JSON.toJSONString(ResultVO.notAuthoried("未授权应用"))).getBytes(StandardCharsets.UTF_8);
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        String url  = request.getURI().getPath();

        System.out.println("请求 url:"+url);

        System.out.println("header uri="+JSON.toJSONString(request.getHeaders()));
//        String token = request.getHeaders().getFirst("test-flux");
//        if(token == null){
//            DataBuffer wrap = serverWebExchange.getResponse().bufferFactory().wrap(UNAUTHORIZED_INFO);
//          //  ServerHttpRequest authErrorReq = request.mutate().path("/auth/error").build();
//            //ServerHttpResponse authFailedRep = response.
//            //erverWebExchange.mutate类似，构建一个新的ServerWebExchange
//          //  ServerWebExchange authErrorExchange = serverWebExchange.mutate().request(authErrorReq).re.build();
//            return  response.writeWith(Flux.just(wrap));
//        }
        return webFilterChain.filter(serverWebExchange);
    }
}
