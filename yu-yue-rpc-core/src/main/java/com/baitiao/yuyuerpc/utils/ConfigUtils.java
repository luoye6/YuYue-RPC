package com.baitiao.yuyuerpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import io.netty.util.internal.StringUtil;

public class ConfigUtils {
    /**
     * 加载配置对象
     * @param tClass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass,String prefix){
        return loadConfig(tClass,prefix,"");
    }

    /**
     * 加载配置对象，支持区分环境
     * @param tClass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass,String prefix,String environment){
        StringBuilder configBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configBuilder.append("-").append(environment);
        }
        configBuilder.append(".properties");
        Props props = new Props(configBuilder.toString());
        return props.toBean(tClass,prefix);
    }

}