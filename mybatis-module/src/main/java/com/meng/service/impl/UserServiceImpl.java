package com.meng.service.impl;

import com.meng.dao.UserDao;
import com.meng.domain.User;
import com.meng.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        User user = userDao.getUserById(id);
        return user;
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUserById(Long id) {
        return userDao.deleteUserById(id);
    }
}
