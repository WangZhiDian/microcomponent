package com.meng.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security的核心配置类是 WebSecurityConfigurerAdapter抽象类，这是权限管理启动的入口，
 * 这里我们自定义一个实现类去实现它。
 */
@Configuration
@EnableWebSecurity//开启Spring Security的功能
//prePostEnabled属性决定Spring Security在接口前注解是否可用@PreAuthorize,@PostAuthorize等注解,设置为true,会拦截加了这些注解的接口
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    // 测试的时候，可以在该类直接配置configure，在内存中设置两个测试的用户名账号，如果正是环境
    // 需要在继承了UserDetailsService接口的类中，实现loadUserByUsername方法，从数据库中去查询用户信息
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       *//* *
        * 基于内存的方式，创建两个用户admin/123456，user/123456
        * *//*
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123456");// 密码
        auth.inMemoryAuthentication()
                .withUser("admin")// 用户名
                .password(password)
                .roles("admin"); // 角色
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         //屏蔽CSRF控制
         http.csrf().disable();
    }

    /**
     * 指定加密方式
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}