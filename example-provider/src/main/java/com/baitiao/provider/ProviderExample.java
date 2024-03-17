package com.baitiao.provider;


import com.baitiao.common.service.UserService;
import com.baitiao.yuyuerpc.RpcApplication;
import com.baitiao.yuyuerpc.config.RegistryConfig;
import com.baitiao.yuyuerpc.config.RpcConfig;
import com.baitiao.yuyuerpc.model.ServiceMetaInfo;
import com.baitiao.yuyuerpc.registry.LocalRegistry;
import com.baitiao.yuyuerpc.registry.Registry;
import com.baitiao.yuyuerpc.registry.RegistryFactory;
import com.baitiao.yuyuerpc.server.HttpServer;
import com.baitiao.yuyuerpc.server.VertxHttpServer;

/**
 * 提供程序示例
 * 服务提供者示例
 *
 * @author cong
 * @date 2024/03/08
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC的框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}