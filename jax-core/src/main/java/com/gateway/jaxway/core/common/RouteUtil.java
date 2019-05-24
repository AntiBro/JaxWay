package com.gateway.jaxway.core.common;

import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.util.pattern.PathPatternParser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author huaili
 * @Date 2019/5/5 19:53
 * @Description RouteUtil for JaxWay
 **/
public class RouteUtil {
    private static Log logger = new DefaultLogImpl(com.gateway.jaxway.core.common.RouteUtil.class);

    public static RouteDefinition generatePathRouteDefition(String url, String path, String Id, Integer order) throws URISyntaxException {
        RouteDefinition rdf = new RouteDefinition();

        PredicateDefinition pd = new PredicateDefinition(PredicatesEnum.getPathPredicateValue(path));
        FilterDefinition filter = new FilterDefinition(FiltersEnum.defaultStripPrefixValueOf());

        rdf.setFilters(new ArrayList<>(Arrays.asList(filter)));
        rdf.setUri(new URI(url));
        rdf.setPredicates(new ArrayList<>(Arrays.asList(pd)));

        if(!StringUtils.isEmpty(Id))
            rdf.setId(Id);
        if(!StringUtils.isEmpty(order))
            rdf.setOrder(order);

        return rdf;
    }


    public static RouteDefinition generatePathRouteDefition(String url,String path) throws URISyntaxException {
        return generatePathRouteDefition(url,path,null,null);
    }


    public static RouteDefinition generateRouteDefition(String routeId,String url,String predicateValue,String filterValue) throws URISyntaxException {
        RouteDefinition rdf = new RouteDefinition();
        PredicateDefinition pd = new PredicateDefinition(predicateValue);
        FilterDefinition filter = new FilterDefinition(filterValue);

        if(!StringUtils.isEmpty(routeId))
            rdf.setId(routeId);
        rdf.setUri(new URI(url));
        rdf.setPredicates(new ArrayList<>(Arrays.asList(pd)));
        rdf.setFilters(new ArrayList<>(Arrays.asList(filter)));

        return rdf;
    }
    // check pathpattern from the server
    public static boolean checkPathPatternList(List<PredicateDefinition> predicateDefinitions){
        PathPatternParser pathPatternParser = new PathPatternParser();
        for(PredicateDefinition predicateDefinition:predicateDefinitions){
            if(predicateDefinition.getName().equals(PredicatesEnum.PATH.FactoryName())){
                Map<String,String> paths = predicateDefinition.getArgs();
                for(String path:paths.values()){
                    try {
                        pathPatternParser.parse(path);
                    }catch (Exception e){
                        logger.log(Log.LogType.ERROR,"checkPathPatternList find error pathPattern from portal server,path ={}",path);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
