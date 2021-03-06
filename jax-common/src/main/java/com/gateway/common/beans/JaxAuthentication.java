package com.gateway.common.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author huaili
 * @Date 2019/4/23 15:00
 * @Description JaxAuthentication
 **/
public class JaxAuthentication {

    public JaxAuthentication(){

    }
    public JaxAuthentication(OpType opType){
        this.opType = opType;
    }

    private String publisher;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishDate;

    private OpType opType;

    private long versionId;

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


    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }
}
