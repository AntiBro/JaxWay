package com.gateway.jaxway.core.vo;


import org.springframework.http.HttpStatus;

/**
 * @Author huaili
 * @Date 2019/4/10 20:55
 * @Description 统一信息返回类
 **/
public class ResultVO {
    private String status;
    private String code;
    private Object content;

    public ResultVO(){

    }
    public ResultVO(String code, String status, Object content){
        this.code = code;
        this.status = status;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static ResultVO notAuthoried(Object content){
        return new ResultVO(HttpStatus.UNAUTHORIZED.value()+"",HttpStatus.UNAUTHORIZED.getReasonPhrase(),content);
    }
}
