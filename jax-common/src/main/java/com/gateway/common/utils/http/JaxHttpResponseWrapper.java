package com.gateway.common.utils.http;

/**
 * @Author huaili
 * @Date 2019/4/22 21:15
 * @Description JaxHttpResponseWrapper
 **/
public class JaxHttpResponseWrapper<T> {
    private String status;
    private Integer code;
    private T body;

    public JaxHttpResponseWrapper(){

    }
    public JaxHttpResponseWrapper(String status,Integer code,T body){
        this.status = status;
        this.code = code;
        this.body = body;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }



}
