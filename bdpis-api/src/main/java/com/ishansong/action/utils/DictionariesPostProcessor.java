package com.ishansong.action.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by yangguoliang on 2017/9/20 下午3:31.
 * <p>
 * 利用spring 的  BeanPostProcessor  初始化时加载数据到缓存当中
 */
public class DictionariesPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object obj, String s) throws BeansException {
        if(obj instanceof DictionariesLoad){
            ((DictionariesLoad)obj).loadData();
        }
        return obj;
    }

    @Override
    public Object postProcessAfterInitialization(Object arg0, String s) throws BeansException {
        return arg0;
    }
}