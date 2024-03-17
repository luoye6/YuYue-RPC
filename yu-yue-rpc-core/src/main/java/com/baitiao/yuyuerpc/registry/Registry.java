package com.baitiao.yuyuerpc.registry;

import com.baitiao.yuyuerpc.config.RegistryConfig;
import com.baitiao.yuyuerpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心接口 其他实现类可以比如 ETCD  Zookeeper 自行实现，可以利用 SPI 动态加载
 * @author 程序员小白条
 * @date 2024/3/17 19:57
 * @gitee https://gitee.com/falle22222n-leaves/vue_-book-manage-system
 */
public interface Registry {
    /**
     * 初始化
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务(服务端)
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务(服务端)
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现(获取某服务的所有节点，消费端)
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);


    /**
     * 服务销毁
     */
    void destroy();

}
