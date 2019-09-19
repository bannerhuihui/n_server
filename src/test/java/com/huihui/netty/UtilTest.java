package com.huihui.netty;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.NettyCliention;
import com.huihui.netty.util.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @className: UtilTest
 * @description:
 * @author: huihui
 * @createDate: 2019-09-04
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {

    @Test
    public void testBase64(){
        String server = Base64Util.encode("Server");
        System.out.println(server);
        String decode = Base64Util.decode(server);
        System.out.println(decode);
    }

    @Test
    public void testUserState(){
        NettyCliention.changeUserState(1024,"XCX",true);
        /*NettyCliention.changeUserState(1024,"PZB",true);
        NettyCliention.changeUserState(1024,"KF",true);
        NettyCliention.changeUserState(1024,"GZH",true);
        NettyCliention.changeUserState(1024,"XCX",true);*/
        System.out.println(JSONObject.toJSONString(NettyCliention.USER_STATE.get(1024).toLongArray()));
    }
}
