package com.baitiao.consumer;

import com.baitiao.yuyuerpc.config.RpcConfig;
import com.baitiao.yuyuerpc.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpc);
    }
}
