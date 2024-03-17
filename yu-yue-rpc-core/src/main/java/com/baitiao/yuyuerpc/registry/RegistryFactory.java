package com.baitiao.yuyuerpc.registry;

import com.baitiao.yuyuerpc.spi.SpiLoader;

/**
 * 注册中心工厂 (用于获取注册中心对象)
 * @author 程序员小白条
 * @date 2024/3/17 20:13
 * @gitee https://gitee.com/falle22222n-leaves/vue_-book-manage-system
 */
public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Registry getInstance(String key){
        return SpiLoader.getInstance(Registry.class,key);
    }
}
