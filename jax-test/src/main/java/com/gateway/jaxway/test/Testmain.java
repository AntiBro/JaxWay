package com.gateway.jaxway.test;

import com.alibaba.fastjson.JSON;
import com.gateway.jaxway.core.utils.http.HttpUtil;
import com.gateway.jaxway.core.utils.http.JaxHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huaili
 * @Date 2019/4/23 15:31
 * @Description TODO
 **/
public class Testmain {
    public static void main(String[] ags){
        JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl("http://127.0.0.1:8720/testAuthor").build();

        HttpUtil httpUtil = HttpUtil.newInstance();
        int n = 1;
        long startTime = System.currentTimeMillis();
        List<JaxHttpRequest> list =new ArrayList<>();
        for(int i=0;i<n;i++){
            list.add(jaxHttpRequest);
        }
       // list.parallelStream().forEach(e->{
            System.out.println(JSON.toJSONString(httpUtil.doGet(jaxHttpRequest)));
       // });
        long endTime = System.currentTimeMillis();

        System.out.println("cost time="+(endTime-startTime));
    }
}
