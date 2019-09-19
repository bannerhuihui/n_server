package com.huihui.netty.strategy.server.check.impl;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.strategy.server.check.CheckTo;

import java.util.ArrayList;
import java.util.List;

public class CheckToDefault extends CheckTo {

    @Override
    public List<String> checkTo(ReadMessage message) {
        List<String> checkto = new ArrayList<>();
        return null;
    }
}
