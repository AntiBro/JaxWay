import com.alibaba.fastjson.JSON;
import com.gateway.common.JaxwayCoder;
import com.gateway.common.beans.OpType;
import com.gateway.jaxway.admin.JaxAdmin;
import com.gateway.jaxway.admin.beans.RouteDefinition;
import com.gateway.jaxway.admin.dao.mapper.JaxwayRouteModelMapper;
import com.gateway.jaxway.admin.dao.mapper.UserModelMapper;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.dao.support.RoleType;
import com.gateway.jaxway.admin.util.RouteUtil;
import com.gateway.jaxway.core.common.FiltersEnum;
import com.gateway.jaxway.core.common.PredicatesEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @Author huaili
 * @Date 2019/5/16 17:29
 * @Description SpringBootTest
 **/
@SpringBootTest(classes = JaxAdmin.class)
@RunWith(SpringRunner.class)
public class TestSuit {

    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private JaxwayRouteModelMapper jaxwayRouteModelMapper;

    @Autowired
    private JaxwayCoder jaxwayCoder;


    //@Test
    public void insertUser1() throws UnsupportedEncodingException {
        String username = "admin";
        String psw = "123456";
        UserModel userModel = new UserModel();
        userModel.setUserName(username);
        userModel.setPsw(jaxwayCoder.encode(username+psw));
        userModel.setAvatar("http://img.qqzhi.com/uploads/2018-12-02/020417940.jpg");
        userModel.setEmail("123456@qq.com");
        userModel.setRoleType(RoleType.COMMON_USER.valueOf());
        userModelMapper.insert(userModel);
    }


    @Test
    public void insertRoute1() throws URISyntaxException {
        JaxwayRouteModel jaxwayRouteModel = new JaxwayRouteModel();
        RouteDefinition routeDefinition = RouteUtil.generatePathRouteDefition("http://127.0.0.1:8720","/testflux,/testflux/**");

        jaxwayRouteModel.setRouteId(routeDefinition.getId());
        jaxwayRouteModel.setJaxwayServerId(1);
        jaxwayRouteModel.setUrl("http://127.0.0.1:8720");
        jaxwayRouteModel.setPredicateType(PredicatesEnum.PATH.FactoryName());
        jaxwayRouteModel.setPrediceteValue("/testflux,/testflux/**");

        jaxwayRouteModel.setFilterType(FiltersEnum.STRIP_PREFIX.getFilterName());
        jaxwayRouteModel.setFilterValue("1");

        jaxwayRouteModel.setOpType(OpType.ADD_ROUTE.ordinal());

        jaxwayRouteModel.setCreateUserId(1);
        jaxwayRouteModel.setRouteContent(JSON.toJSONString(routeDefinition));
        jaxwayRouteModelMapper.insert(jaxwayRouteModel);
    }
}
