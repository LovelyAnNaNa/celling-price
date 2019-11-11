package com.whtt.cellingprice.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {
    @Autowired
    private InterceptorConfig interceptorConfig;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("大哥好");
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(interceptorConfig).addPathPatterns("/api/**").excludePathPatterns("/sysUser/login","/sysUser/forgetPassword");
    }

}
