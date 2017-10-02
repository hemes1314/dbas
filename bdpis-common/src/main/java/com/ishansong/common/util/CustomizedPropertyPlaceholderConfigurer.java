package com.ishansong.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yangguoliang on 2017/9/5 下午3:32.
 * <p>
 * 使用 spring 的 PropertyPlaceholderConfigurer将系统相关变量分离懂到一个properties文件中，便于系统移植、切换。
 * 将 org.springframework.beans.factory.config.PropertyPlaceholderConfigurer 重新自定义
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
}
