package com.meng.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigFilter {

    @Bean
    public FilterRegistrationBean RegistTest1(){
        //通过FilterRegistrationBean实例设置优先级可以生效
        //通过@WebFilter无效
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CustormFilter01());//注册自定义过滤器
        bean.setName("custom filter 1");//过滤器名称
        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(1);//优先级，最顶级
        return bean;
    }
    @Bean
    public FilterRegistrationBean RegistTest2(){
        //通过FilterRegistrationBean实例设置优先级可以生效
        //通过@WebFilter无效
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CustormFilter02());//注册自定义过滤器
        bean.setName("custom filter 2");//过滤器名称
        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(6);//优先级，越低越优先
        return bean;
    }


}
