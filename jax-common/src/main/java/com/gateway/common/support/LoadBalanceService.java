package com.gateway.common.support;

import org.springframework.util.CollectionUtils;

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
            if(CollectionUtils.isEmpty(servers)){
                throw new NullPointerException("Jaxway admin potral server list can not be null");
            }
            Random random = new Random();
            if(servers.size() == 1){
                return servers.get(0);
            }
            return servers.get(random.nextInt(servers.size()));
        }
    };
}
