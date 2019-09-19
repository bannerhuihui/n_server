package com.huihui.netty;

import com.huihui.netty.common.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: MapTest
 * @description:
 * @author: huihui
 * @createDate: 2019-09-04
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapTest {

    private static Map<String,Integer> testMap = new ConcurrentHashMap<>();

    @Test
    public void testConcurrentHashMap(){
        System.out.println(ErrorCode.MESSAGE_CC_ERROR.getCode());
    }
}
