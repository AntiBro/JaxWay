package com.gateway.jaxway.server.validator;

import com.gateway.jaxway.support.beans.JaxRouteDefinition;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.CompletableFuture;

/**
 * @Author huaili
 * @Date 2019/5/24 11:44
 * @Description JaxServerRouteDefinitionValidator
 **/
public interface JaxServerRouteDefinitionValidator extends DisposableBean {

    void addJaxServer(String JaxwayServerId);

    CompletableFuture<Boolean> verifyRouteDefintion(String JaxwayServerId, JaxRouteDefinition jaxRouteDefinition) throws InterruptedException;
}
