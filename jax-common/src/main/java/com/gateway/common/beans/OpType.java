package com.gateway.common.beans;

/**
 * @Author huaili
 * @Date 2019/4/23 15:26
 * @Description OpType
 **/
public enum OpType {
    NONE,// 0
    ADD_APP,// 1
    DELETE_APP,// 2
    ADD_WHITE_SERVER_APP, // 3
    DELETE_WHITE_SERVER_APP,// 4
    ADD_ROUTE,// 5
    DELETE_ROUTE,// 6
    UPDATE_ROUTE;// 7

    public static OpType valueOf(int val){
        for(OpType opType:OpType.values()){
            if(opType.ordinal() == val){
                return opType;
            }
        }
        return NONE;
    }
}
