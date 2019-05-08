package com.gateway.common.utils.http;

/**
 * @Author huaili
 * @Date 2019/4/22 21:13
 * @Description JaxHttpRequest
 **/
public class JaxHttpRequest {

    private String requestUrl;

    private int connectionTimeOut;

    private int readTimeOut;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private String requestUrl;

        private int connectionTimeOut = -1;

        private int readTimeOut = -1;

        public Builder requestUrl(String requestUrl){
            this.requestUrl = requestUrl;
            return this;
        }
        public Builder connectionTimeOut(int connectionTimeOut){
            this.connectionTimeOut = connectionTimeOut;
            return this;
        }
        public Builder readTimeOut(int readTimeOut){
            this.readTimeOut = readTimeOut;
            return this;
        }
        public JaxHttpRequest build(){
            JaxHttpRequest jaxHttpRequest = new JaxHttpRequest();
            jaxHttpRequest.setConnectionTimeOut(this.connectionTimeOut);
            jaxHttpRequest.setReadTimeOut(this.readTimeOut);
            jaxHttpRequest.setRequestUrl(this.requestUrl);
            return jaxHttpRequest;
        }


    }
}
