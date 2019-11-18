package com.whtt.cellingprice.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    private InterceptorConfig interceptorConfig;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(interceptorConfig).
                addPathPatterns("/**").
                excludePathPatterns("/sysUser/login",
                        "/static/**",
                        "/admin/sysCustomer/insertCustomerOrAddIntegral",
                        "/admin/sysCustomer/customerInfo",
                        "/sysAccount/offer",
                        "/**");
    }

}
