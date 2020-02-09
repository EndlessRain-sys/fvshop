package com.hdw.fvshop.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    // 需要加密的字段数组
    private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

    /**
     * 对关键的属性进行转换
     */
}