package com.gateway.common.util;

/**
 * @Author huaili
 * @Date 2019/5/10 16:51
 * @Description VersionUtil
 **/
public class VersionUtil {
    public static boolean checkVerion(long repVerionId,long localVerionId){
        if(repVerionId != 0 && repVerionId > localVerionId){
            return true;
        }
        return false;
    }
}
