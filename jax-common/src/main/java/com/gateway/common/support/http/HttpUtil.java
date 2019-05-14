package com.gateway.common.support.http;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @Author huaili
 * @Date 2019/4/22 21:17
 * @Description HttpUtil
 **/
public class HttpUtil {

    public static final int SUCCESS_CODE = 200;


    public static HttpUtil newInstance() {
        return new HttpUtil();
    }

    private RestTemplate restTemplate = new RestTemplate();

    private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory;

    public <T> T doGet(JaxHttpRequest jaxHttpRequest, ParameterizedTypeReference<T> responseBodyType){

        if(simpleClientHttpRequestFactory == null){
            simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        }
        simpleClientHttpRequestFactory.setConnectTimeout(jaxHttpRequest.getConnectionTimeOut());
        simpleClientHttpRequestFactory.setReadTimeout(jaxHttpRequest.getReadTimeOut());

        restTemplate.setRequestFactory(simpleClientHttpRequestFactory);

        T responseEntity = exchange(restTemplate,jaxHttpRequest.getRequestUrl(),HttpMethod.GET,responseBodyType,null);
        return responseEntity;
    }


    public  <T, A> T exchange(RestTemplate restTemplate,String url, HttpMethod method, ParameterizedTypeReference<T> responseBodyType, A requestBody) {
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));

        headers.setContentType(mediaType);

        HttpEntity<A> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, responseBodyType);
        return resultEntity.getBody();
    }


}
