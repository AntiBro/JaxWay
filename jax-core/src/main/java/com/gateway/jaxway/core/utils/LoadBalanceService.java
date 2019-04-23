package com.gateway.jaxway.core.utils;

import java.util.List;
import java.util.Random;

/**
 * @Author huaili
 * @Date 2019/4/23 19:56
 * @Description LoadBalanceService
 **/
public interface LoadBalanceService {
    String selectServer(List<String> servers);

    LoadBalanceService RandomLoadBalanceService = new LoadBalanceService() {
        @Override
        public String selectServer(List<String> servers) {
            Random random = new Random();
            if(servers.size() == 1){
                return servers.get(0);
            }
            return servers.get(random.nextInt(servers.size()));
        }
    };
}
