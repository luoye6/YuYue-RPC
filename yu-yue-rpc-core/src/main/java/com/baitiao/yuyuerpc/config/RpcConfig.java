package com.baitiao.yuyuerpc.config;

import lombok.Data;

@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "yu-rpc";
    /**
     * 版本号
     */
    private String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";
    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;
    /**
     * Mock 模拟调用 默认为 false
     */
    private boolean mock = false;
}
