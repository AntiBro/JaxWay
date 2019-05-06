package com.gateway.jaxway.core.access;


import com.gateway.jaxway.core.common.TimeUtil;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author huaili
 * @Date 2019/5/5 21:18
 * @Description AccessLogGlobalFilter for record the accesslog
 **/
public class AccessLogGlobalFilter implements GlobalFilter, Ordered {


    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String REQUEST_PREFIX = "Request Info [ ";

    private static final String REQUEST_TAIL = " ]";

    private static final String RESPONSE_PREFIX = "Response Info [ ";

    private static final String RESPONSE_TAIL = " ]";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);
        InetSocketAddress address = requestDecorator.getRemoteAddress();
        HttpMethod method = requestDecorator.getMethod();
        URI url = requestDecorator.getURI();
        HttpHeaders headers = requestDecorator.getHeaders();
        Flux<DataBuffer> body = requestDecorator.getBody();
        //读取requestBody传参
        AtomicReference<String> requestBody = new AtomicReference<>("");
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            requestBody.set(charBuffer.toString());
        });
        String requestParams = requestBody.get();
        StringBuilder accessMsg = new StringBuilder();
        accessMsg.append(REQUEST_PREFIX);
        accessMsg.append(TimeUtil.getCurrentTime());
        accessMsg.append(";  header=").append(headers);
        accessMsg.append(";  params=").append(requestParams);
        accessMsg.append(";  address=").append(address.getHostName() + address.getPort());
        accessMsg.append(";  method=").append(method.name());
        accessMsg.append(";  url=").append(url.getPath());
        accessMsg.append(REQUEST_TAIL);

        ServerHttpResponse response = exchange.getResponse();

        DataBufferFactory bufferFactory = response.bufferFactory();
        accessMsg.append(RESPONSE_PREFIX);
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        long endTime = System.currentTimeMillis();
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                       // String responseResult = new String(content, Charset.forName("UTF-8"));
                        accessMsg.append(TimeUtil.getCurrentTime());
                        accessMsg.append(" status=").append(this.getStatusCode());
                        accessMsg.append("; header=").append(this.getHeaders());
                       // accessMsg.append("; responseResult=").append(responseResult);
                        accessMsg.append("; cost= ").append(endTime-startTime).append("ms");
                        accessMsg.append(RESPONSE_TAIL);
                        log.info(accessMsg.toString());
                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body); // if body is not a flux. never got there.
            }
        };

        return chain.filter(exchange.mutate().request(requestDecorator).response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }

}
