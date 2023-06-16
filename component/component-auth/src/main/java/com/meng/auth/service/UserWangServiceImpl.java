package com.meng.auth.service;

import com.meng.auth.bean.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserWangServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 1/通过userName 获取到userInfo信息
         * 2/通过User（UserDetails）返回details。
         * 该user信息，应该从数据库中去查询出来对比
         */
        UserInfo userInfo = new UserInfo(1, "wang", "123");
        //调用usersMapper方法，根据用户名查询数据库
//        QueryWrapper<Users> wrapper = new QueryWrapper();
//        // where username=?
//        wrapper.eq("username",username);
//        User user = userMapper.selectOne(wrapper);
        //判断
        if(userInfo == null) {//数据库没有用户名，认证失败
            throw  new UsernameNotFoundException("用户名不存在！");
        }

        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_sale");

        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ "admin"));
        User userDetails = new User(userInfo.getUserName(), passwordEncoder.encode(userInfo.getPassword()), authorities);

        //从查询数据库返回users对象，得到用户名和密码，返回
        return userDetails;
    }
}
