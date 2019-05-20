package com.gateway.jaxway.admin.vo;

import com.gateway.jaxway.core.common.FiltersEnum;
import com.gateway.jaxway.core.common.PredicatesEnum;

/**
 * @Author huaili
 * @Date 2019/5/20 14:50
 * @Description GatewayVO
 **/
public class GatewayVO {
    private String name;
    private String demoValue;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDemoValue() {
        return demoValue;
    }

    public void setDemoValue(String demoValue) {
        this.demoValue = demoValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static  GatewayVO[] getAllPredicatesInfo(){
        GatewayVO[] gatewayVOS = new GatewayVO[PredicatesEnum.values().length];
        int i = 0;
        for(PredicatesEnum predicatesEnum:PredicatesEnum.values()){
            GatewayVO gatewayVO = new GatewayVO();
            gatewayVO.setName(predicatesEnum.FactoryName());
            gatewayVO.setDemoValue(predicatesEnum.getDemoValue());
            gatewayVO.setDescription(predicatesEnum.getDescriptions());
            gatewayVOS[i] = gatewayVO;
            i++;
        }
        return gatewayVOS;
    }


    public static  GatewayVO[] getAllFilterInfo(){
        GatewayVO[] gatewayVOS = new GatewayVO[FiltersEnum.values().length];
        int i = 0;
        for(FiltersEnum filtersEnum:FiltersEnum.values()){
            GatewayVO gatewayVO = new GatewayVO();
            gatewayVO.setName(filtersEnum.getFilterName());
            gatewayVO.setDemoValue(filtersEnum.getDemoValue());
            gatewayVO.setDescription(filtersEnum.getDescriptions());
            gatewayVOS[i] = gatewayVO;
            i++;
        }
        return gatewayVOS;
    }

}
