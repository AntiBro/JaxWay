package com.gateway.jaxway.core.authority.server;

import com.gateway.jaxway.core.authority.JaxwayCoder;
import com.gateway.jaxway.core.authority.JaxwayWhiteList;
import com.gateway.jaxway.core.authority.impl.Base64JaxwayCoder;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * @Author huaili
 * @Date 2019/5/6 14:14
 * @Description LocalJaxwayWhiteList
 **/
public class LocalJaxwayWhiteList implements JaxwayWhiteList {

    private static volatile List<String> cachedWhiteList = new CopyOnWriteArrayList();

    private static JaxwayWhiteList INSTANCE = new LocalJaxwayWhiteList();

    private JaxwayCoder jaxwayCoder;
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
        if(!cachedWhiteList.contains(uriRegx)){
            log.log("add white url regx, value="+uriRegx);
            cachedWhiteList.add(uriRegx);
        }
    }

    @Override
    public void remove(String uriRegx) {
        try {
            uriRegx = jaxwayCoder.decode(uriRegx);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        log.log("remove white url regx, value="+uriRegx);
        cachedWhiteList.remove(uriRegx);
    }

    @Override
    public boolean match(String uri) {
        for(String regx:cachedWhiteList){
            Pattern p = Pattern.compile(regx);
            if(p.matcher(uri).matches()) {
                log.log(Log.LogType.TRACE,"uri="+uri+" matched white app list");
                return true;
            }
        }
        log.log(Log.LogType.TRACE,"uri="+uri+" is black list");

        return false;
    }
}
