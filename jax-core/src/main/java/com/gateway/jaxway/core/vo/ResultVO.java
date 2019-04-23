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
    private Object body;

    /**
     * 未授权的 请求应用
     */
    public static ResultVO NOT_AUTHORIZED_REQUEST = ResultVO.notAuthoried("JaxWay found illegal request");

    public ResultVO(){

    }
    public ResultVO(String code, String status, Object body){
        this.code = code;
        this.status = status;
        this.body = body;
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

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static ResultVO notAuthoried(Object content){
        return new ResultVO(HttpStatus.UNAUTHORIZED.value()+"",HttpStatus.UNAUTHORIZED.getReasonPhrase(),content);
    }

    public static ResultVO success(Object content){
        return new ResultVO(HttpStatus.OK.value()+"",HttpStatus.OK.getReasonPhrase(),content);
    }
}
