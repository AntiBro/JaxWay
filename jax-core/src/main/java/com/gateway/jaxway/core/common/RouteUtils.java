package com.gateway.jaxway.core.common;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author huaili
 * @Date 2019/5/5 19:53
 * @Description RouteUtils for JaxWay
 **/
public class RouteUtils {

    public static RouteDefinition generatePathRouteDefition(String url,String path,String Id,Integer order) throws URISyntaxException {
        RouteDefinition rdf = new RouteDefinition();

        PredicateDefinition pd = new PredicateDefinition(PredicatesEnum.getPathPredicateValue(path));

        rdf.setPredicates(new ArrayList<>(Arrays.asList(pd)));

        FilterDefinition filter = new FilterDefinition(FiltersEnum.defaultStripPrefixValueOf());

        rdf.setFilters(new ArrayList<>(Arrays.asList(filter)));

        rdf.setUri(new URI(url));

        if(!StringUtils.isEmpty(Id))
            rdf.setId(Id);
        if(!StringUtils.isEmpty(order))
            rdf.setOrder(order);

        return rdf;
    }


    public static RouteDefinition generatePathRouteDefition(String url,String path) throws URISyntaxException {
        return generatePathRouteDefition(url,path,null,null);
    }
}
