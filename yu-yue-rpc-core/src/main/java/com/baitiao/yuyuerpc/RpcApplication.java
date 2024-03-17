package com.baitiao.yuyuerpc;

import com.baitiao.yuyuerpc.config.RegistryConfig;
import com.baitiao.yuyuerpc.config.RpcConfig;
import com.baitiao.yuyuerpc.constant.RpcConstant;
import com.baitiao.yuyuerpc.registry.Registry;
import com.baitiao.yuyuerpc.registry.RegistryFactory;
import com.baitiao.yuyuerpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}",newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init,config = {}",registryConfig);
    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            // 配置加载失败，使用默认值
            System.out.println(e);
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     * @return
     */
    public static RpcConfig getRpcConfig(){
        if(rpcConfig==null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
