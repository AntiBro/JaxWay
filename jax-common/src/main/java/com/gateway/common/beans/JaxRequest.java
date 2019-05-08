package com.gateway.common.beans;

/**
 * @Author huaili
 * @Date 2019/4/21 17:39
 * @Description JaxRequest
 **/
public class JaxRequest {
    private String id;
    private String url;
    private String token;


    private JaxRequest(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String url;
        private String token;


        public Builder appId(String appId){
            this.id = appId;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }
        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public JaxRequest build(){
            JaxRequest jaxRequest = new JaxRequest();

            jaxRequest.setUrl(this.url);
            jaxRequest.setToken(this.token);
            jaxRequest.setId(this.id);
            return jaxRequest;
        }
    }
}
