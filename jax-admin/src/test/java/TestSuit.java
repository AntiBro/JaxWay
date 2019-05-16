import com.gateway.common.JaxwayCoder;
import com.gateway.jaxway.admin.JaxAdmin;
import com.gateway.jaxway.admin.dao.mapper.UserModelMapper;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.dao.support.RoleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

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
    private JaxwayCoder jaxwayCoder;


    @Test
    public void insertUser1() throws UnsupportedEncodingException {
        String username = "admin";
        String psw = "123456";
        UserModel userModel = new UserModel();
        userModel.setUserName("admin");
        userModel.setPsw(jaxwayCoder.encode(username+psw));
        userModel.setAvatar("http://img.qqzhi.com/uploads/2018-12-02/020417940.jpg");
        userModel.setEmail("123456@qq.com");
        userModel.setRoleType(RoleType.COMMON_USER.valueOf());
        userModelMapper.insert(userModel);
    }
}
