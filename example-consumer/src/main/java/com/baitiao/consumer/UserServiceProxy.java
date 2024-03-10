package com.baitiao.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baitiao.common.model.User;
import com.baitiao.common.service.UserService;
import com.baitiao.yuyuerpc.model.RpcRequest;
import com.baitiao.yuyuerpc.model.RpcResponse;
import com.baitiao.yuyuerpc.serializer.JdkSerializer;
import com.baitiao.yuyuerpc.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理，由于接口不灵活，每次都要去单独写一个实现类，因此采用动态代理
 */
public class UserServiceProxy implements UserService {

    public User getUser(User user) {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}