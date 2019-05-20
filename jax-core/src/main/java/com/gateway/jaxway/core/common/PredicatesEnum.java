package com.gateway.jaxway.core.common;

/**
 * @Author huaili
 * @Date 2019/5/5 17:00
 * @Description PredicateDefinition Enum
 **/
public enum PredicatesEnum {
    AFTER("After",
            "2017-01-20T17:42:47.789-07:00[America/Denver]",
            "This route matches any request after Jan 20, 2017 17:42 Mountain Time (Denver)."),

    BEFORE("Before",
            "2017-01-20T17:42:47.789-07:00[America/Denver]",
            "This route matches any request before Jan 20, 2017 17:42 Mountain Time (Denver)."),

    BETWEEN("Between",
            "2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]",
            "This route matches any request after Jan 20, 2017 17:42 Mountain Time (Denver) and before Jan 21, 2017 17:42 Mountain Time (Denver). This could be useful for maintenance windows"),

    COOKIE("Cookie",
            "chocolate, ch.p",
            "This route matches the request has a cookie named chocolate who’s value matches the ch.p regular expression"),

    HEADER("Header",
            "X-Request-Id, \\d+",
            "his route matches if the request has a header named X-Request-Id whos value matches the \\d+ regular expression (has a value of one or more digits)."),

    HOST("Host",
            "**.somehost.org,**.anotherhost.org",
            "This route would match if the request has a Host header has the value www.somehost.org or beta.somehost.org or www.anotherhost.org."),

    METHOD("Method",
            "GET",
            "This route would match if the request method was a GET."),

    PATH("Path",
            "/foo/{segment},/bar/{segment}",
            "This route would match if the request path was, for example: /foo/1 or /foo/bar or /bar/baz."),

    QUERY("Query",
            "foo, ba.",
            "This route would match if the request contained a foo query parameter whose value matched the ba. regexp, so bar and baz would match"),

    REMOTEADDR("RemoteAddr",
            "192.168.1.1/24",
            "This route would match if the remote address of the request was, for example, 192.168.1.10.")
    ;

    private String predicateFactoryName;

    private String demoValue;

    private String descriptions;

    PredicatesEnum(String predicateFactoryName,String demoValue,String descriptions){
        this.predicateFactoryName = predicateFactoryName;
        this.demoValue = demoValue;
        this.descriptions = descriptions;
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

    public static String getPathPredicateValue(String path){
        return PATH.predicateFactoryName+"="+path;
    }

    public String FactoryName(){
        return this.predicateFactoryName;
    }

}
