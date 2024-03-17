package com.baitiao.yuyuerpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baitiao.yuyuerpc.RpcApplication;
import com.baitiao.yuyuerpc.config.RpcConfig;
import com.baitiao.yuyuerpc.constant.RpcConstant;
import com.baitiao.yuyuerpc.model.RpcRequest;
import com.baitiao.yuyuerpc.model.RpcResponse;
import com.baitiao.yuyuerpc.model.ServiceMetaInfo;
import com.baitiao.yuyuerpc.registry.Registry;
import com.baitiao.yuyuerpc.registry.RegistryFactory;
import com.baitiao.yuyuerpc.serializer.JdkSerializer;
import com.baitiao.yuyuerpc.serializer.Serializer;
import com.baitiao.yuyuerpc.serializer.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            assert serializer != null;
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // 暂时先取第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            log.error("IOException", e);
        }

        return null;
    }
}