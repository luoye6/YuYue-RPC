package com.baitiao.yuyuerpc.serializer;

import com.baitiao.yuyuerpc.serializer.hessian.HessianSerializer;
import com.baitiao.yuyuerpc.serializer.json.JsonSerializer;
import com.baitiao.yuyuerpc.serializer.kryo.KryoSerializer;
import com.baitiao.yuyuerpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
    /**
     * 通过 SPI 加载器动态加载 实现序列化接口的所有类
     */
    static {
        SpiLoader.load(Serializer.class);
    }
    /**
     * 序列化映射（用于实现单例) 硬编码
     */
//    private static final Map<String,Serializer> KEY_SERIALIZER_MAP = new HashMap<String,Serializer>(){{
//       put(SerializerKeys.JDK,new JdkSerializer());
//       put(SerializerKeys.JSON,new JsonSerializer());
//       put(SerializerKeys.KRYO,new KryoSerializer());
//       put(SerializerKeys.HESSIAN,new HessianSerializer());
//    }};
    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class,key);
    }
}
