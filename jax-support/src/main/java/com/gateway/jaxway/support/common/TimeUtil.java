package com.gateway.jaxway.support.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author huaili
 * @Date 2019/5/5 21:28
 * @Description TimeUtil
 **/
public class TimeUtil {
    public static String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return df.format(new Date());
    }
}
