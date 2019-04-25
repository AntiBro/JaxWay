package com.gateway.jaxway.core.utils.http;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author huaili
 * @Date 2019/4/23 15:00
 * @Description JaxAuthentication
 **/
public class JaxAuthentication {

    public static JaxAuthentication NONE = new JaxAuthentication(OpType.NONE);


    public JaxAuthentication(){

    }
    public JaxAuthentication(OpType opType){
        this.opType = opType;
    }

    private String url;

    private String token;

    private String publisher;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishDate;

    private OpType opType;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }


    public OpType getOpType() {
        return opType;
    }

    public void setOpType(OpType opType) {
        this.opType = opType;
    }




}
