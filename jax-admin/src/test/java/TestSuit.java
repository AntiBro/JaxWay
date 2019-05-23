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
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author huaili
 * @Date 2019/5/16 17:29
 * @Description SpringBootTest
 **/
//@SpringBootTest(classes = JaxAdmin.class)
//@RunWith(SpringRunner.class)
@RunWith(JUnit4.class)
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


   // @Test
    public void insertRoute1() throws URISyntaxException {
        JaxwayRouteModel jaxwayRouteModel = new JaxwayRouteModel();
        RouteDefinition routeDefinition = RouteUtil.generatePathRouteDefition("http://127.0.0.1:8720","/testflux,/testflux/**");

        jaxwayRouteModel.setRouteId(routeDefinition.getId());
        jaxwayRouteModel.setJaxwayServerId(1);
        jaxwayRouteModel.setUrl("http://127.0.0.1:8720");
        jaxwayRouteModel.setPredicateType(PredicatesEnum.PATH.FactoryName());
        jaxwayRouteModel.setPredicateValue("/testflux,/testflux/**");

        jaxwayRouteModel.setFilterType(FiltersEnum.STRIP_PREFIX.getFilterName());
        jaxwayRouteModel.setFilterValue("1");

        jaxwayRouteModel.setOpType(OpType.ADD_ROUTE.ordinal());

        jaxwayRouteModel.setCreateUserId(1);
        jaxwayRouteModel.setRouteContent(JSON.toJSONString(routeDefinition));
        jaxwayRouteModelMapper.insert(jaxwayRouteModel);
    }


    public int longestValidParentheses(String s) {
        int ans = 0;
        int n = s.length();
        Stack st = new Stack();
        int index = 0;
        for (int i = 0; i <n ; i++) {
            if (s.charAt(i) == '(')
                st.push(i);
            else if (st.size() == 0){
                index = i + 1;
                continue;
            }else {
                st.pop();
                if (st.empty()){
                    ans = Math.max(ans,i - index + 1);
                }else {
                    ans = Math.max(ans,i - (int)st.peek());
                }
            }
        }


        return ans;
    }

    @Test
    public void testJunit(){
        System.out.println(longestValidParentheses(")()())"));

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3,3,100, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
        //poolExecutor.submit(null);
        WeakHashMap weakHashMap;
    }
}
