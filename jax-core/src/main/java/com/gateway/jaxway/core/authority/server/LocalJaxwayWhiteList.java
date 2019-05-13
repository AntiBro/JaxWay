package com.gateway.jaxway.core.authority.server;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.JaxwayWhiteList;
import com.gateway.common.defaults.Base64JaxwayCoder;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.http.server.PathContainer.parsePath;

/**
 * @Author huaili
 * @Date 2019/5/6 14:14
 * @Description LocalJaxwayWhiteList
 **/
public class LocalJaxwayWhiteList implements JaxwayWhiteList {

    private static volatile List<PathPattern> cachedWhiteList = new CopyOnWriteArrayList();

    private static JaxwayWhiteList INSTANCE = new LocalJaxwayWhiteList();

    private JaxwayCoder jaxwayCoder;

    private PathPatternParser pathPatternParser = new PathPatternParser();

    private Log log;

    private LocalJaxwayWhiteList(){
        this(new Base64JaxwayCoder(),new DefaultLogImpl(LocalJaxwayWhiteList.class));
    }
    private LocalJaxwayWhiteList(JaxwayCoder jaxwayCoder,Log log){
        this.jaxwayCoder = jaxwayCoder;
        this.log = log;
    }

    public static JaxwayWhiteList create(){
        return INSTANCE;
    }
    @Override
    public void add(String uriRegx) {
        try {
            uriRegx = jaxwayCoder.decode(uriRegx);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ;
        }

        PathPattern pathPattern = pathPatternParser.parse(uriRegx);

        if(!cachedWhiteList.contains(pathPattern)){
            log.log("server add white whiteList, value="+uriRegx);
            cachedWhiteList.add(pathPattern);
        }
    }

    @Override
    public void remove(String uriRegx) {
        try {
            uriRegx = jaxwayCoder.decode(uriRegx);
            PathPattern pathPattern = pathPatternParser.parse(uriRegx);
            if(cachedWhiteList.contains(pathPattern)) {
                log.log("server remove whiteList, value="+uriRegx);
                cachedWhiteList.remove(pathPattern);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public boolean match(String uri) {
        PathContainer path = parsePath(uri);
        Optional<PathPattern> optionalPathPattern = cachedWhiteList.stream().filter(pattern -> pattern.matches(path)).findFirst();
        if(optionalPathPattern.isPresent()) {
            log.log(Log.LogType.DEBUG, "uri=" + uri + " matched white app list");
            return true;
        }
        log.log(Log.LogType.DEBUG,"uri="+uri+" is black list");

        return false;
    }
}
