package com.gateway.jaxway.admin.feignApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author huaili
 * @Date 2019/6/13 10:00
 * @Description TODO
 **/
@FeignClient(value = "ts-api", url = "http://127.0.0.1:8920")
public interface TsApi {

    @RequestMapping("/getDef")
    DefaultTransactionDefinition getTs();

    @RequestMapping("/commit")
    void commit(@RequestBody DefaultTransactionDefinition def);


    @RequestMapping("/rollback")
    void rollback(@RequestBody DefaultTransactionDefinition def);
}
