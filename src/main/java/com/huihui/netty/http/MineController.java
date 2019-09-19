package com.huihui.netty.http;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.NettyCliention;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: MineController
 * @description:
 * @author: huihui
 * @createDate: 2019-09-17
 * @version: 1.0
 */
@RestController
public class MineController  {

    @GetMapping(value = "/test")
    public String message(){
        return JSONObject.toJSONString(NettyCliention.SWITCHES);
    }
}
