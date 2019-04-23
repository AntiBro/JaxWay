package com.gateway.jaxway.core.utils.http;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author huaili
 * @Date 2019/4/22 21:17
 * @Description HttpUtil
 **/
public class HttpUtil {

  public static HttpUtil newInstance() {
        return new HttpUtil();
    }

    private RestTemplate restTemplate = new RestTemplate();

    public  <T> T doGet(JaxHttpRequest jaxHttpRequest,Class<T> clazz){
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        if(simpleClientHttpRequestFactory == null){
            simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        }
        simpleClientHttpRequestFactory.setConnectTimeout(jaxHttpRequest.getConnectionTimeOut());
        simpleClientHttpRequestFactory.setReadTimeout(jaxHttpRequest.getReadTimeOut());

        restTemplate.setRequestFactory(simpleClientHttpRequestFactory);
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(jaxHttpRequest.getRequestUrl(),clazz);

        return responseEntity.getBody();
    }

    public JaxHttpResponseWrapper doGet(JaxHttpRequest jaxHttpRequest){
        return doGet(jaxHttpRequest, JaxHttpResponseWrapper.class);
    }

}
