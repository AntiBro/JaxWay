package com.gateway.jaxway.core.common;

/**
 * @Author huaili
 * @Date 2019/5/5 17:20
 * @Description GatewayFilter Factories
 **/
public enum FiltersEnum {

    STRIP_PREFIX("StripPrefix","1","When a request is made through the gateway to /name/foo the request made to nameservice will look like http://nameservice/foo.");

    private String filterName;
    private String demoValue;
    private String descriptions;
    FiltersEnum(String filterName,String demoValue,String descriptions){
        this.filterName = filterName;
        this.demoValue = demoValue;
        this.descriptions = descriptions;
    }

    public static String defaultStripPrefixValueOf(){
        return STRIP_PREFIX.filterName+"="+STRIP_PREFIX.demoValue;
    }
}
