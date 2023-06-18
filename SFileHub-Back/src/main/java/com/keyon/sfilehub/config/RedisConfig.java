package com.keyon.sfilehub.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author Keyon
 * @data 2023/6/18 14:15
 * @desc RedisConfig
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

}
