package com.gateway.jaxway.core.common;

/**
 * @Author huaili
 * @Date 2019/5/5 17:20
 * @Description GatewayFilter Factories
 **/
public enum FiltersEnum {

    STRIP_PREFIX("StripPrefix","1","When a request is made through the gateway to /name/foo the request made to nameservice will look like http://nameservice/foo."),
    ADD_REQUESTH_EADER("AddRequestHeader","X-Request-Foo, Bar","This will add X-Request-Foo:Bar header to the downstream request’s headers for all matching requests."),
    ADD_REQUEST_PARAMETER("AddRequestParameter","foo, bar","This will add foo=bar to the downstream request’s query string for all matching requests."),
    ADD_RESPONSE_HEADER("AddResponseHeader","X-Response-Foo, Bar","This will add X-Response-Foo:Bar header to the downstream response’s headers for all matching requests."),
    PREFIX_PATH("PrefixPath","/mypath","This will prefix /mypath to the path of all matching requests. So a request to /hello, would be sent to /mypath/hello."),
    REDIRECTTO("RedirectTo","302, http://acme.org","This will send a status 302 with a Location:http://acme.org header to perform a redirect."),
    REMOVE_REQUEST_HEADER("RemoveRequestHeader","X-Request-Foo","This will remove the X-Request-Foo header before it is sent downstream."),
    REMOVE_RESPONSE_HEADER("RemoveResponseHeader","X-Response-Foo","This will remove the X-Response-Foo header from the response before it is returned to the gateway client."),
    REWRITEPATH("RewritePath","/foo/(?<segment>.*), /$\\{segment}","For a request path of /foo/bar, this will set the path to /bar before making the downstream request. Notice the $\\ which is replaced with $ because of the YAML spec"),
    RewriteResponseHeader("RewriteResponseHeader","X-Response-Foo, , password=[^&]+, password=***","For a header value of /42?user=ford&password=omg!what&flag=true, it will be set to /42?user=ford&password=***&flag=true after making the downstream request. Please use $\\ to mean $ because of the YAML spec."),
    SETPATH("SetPath","/{segment}","For a request path of /foo/bar, this will set the path to /bar before making the downstream request."),
    SET_RESPONSE_HEADER("SetResponseHeader","X-Response-Foo, Bar","This GatewayFilter replaces all headers with the given name, rather than adding. So if the downstream server responded with a X-Response-Foo:1234, this would be replaced with X-Response-Foo:Bar, which is what the gateway client would receive."),
    SET_STATUS("SetStatus","401","In either case, the HTTP status of the response will be set to 401."),

    ;




    private String filterName;
    private String demoValue;
    private String descriptions;
    FiltersEnum(String filterName,String demoValue,String descriptions){
        this.filterName = filterName;
        this.demoValue = demoValue;
        this.descriptions = descriptions;
    }

    public String getFilterName(){
        return this.filterName;
    }

    public static String defaultStripPrefixValueOf(){
        return STRIP_PREFIX.filterName+"="+STRIP_PREFIX.demoValue;
    }


    public String getDemoValue() {
        return demoValue;
    }

    public void setDemoValue(String demoValue) {
        this.demoValue = demoValue;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
