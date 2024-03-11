package com.baitiao.provider;

import com.baitiao.common.service.UserService;
import com.baitiao.yuyuerpc.RpcApplication;
import com.baitiao.yuyuerpc.registry.LocalRegistry;
import com.baitiao.yuyuerpc.server.HttpServer;
import com.baitiao.yuyuerpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        // 加载自定义的端口
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
