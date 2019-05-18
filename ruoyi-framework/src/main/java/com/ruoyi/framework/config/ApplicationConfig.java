package com.ruoyi.framework.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解
 * @author Administrator
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.ruoyi.*.mapper")
public class ApplicationConfig {

}
