package com.meng.service.impl;

import com.meng.dao.InsertUserDao;
import com.meng.domain.User;
import com.meng.service.InsertUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InsertUserServiceImpl implements InsertUserService {


    @Resource
    InsertUserDao insertUserDao;

    @Override
    public int insertUserWithPrimaryKey(User user) {
        return insertUserDao.insertUserWithPrimaryKey(user);
    }

    @Override
    public int insertUserWithGenPrimaryKey(User user) {
        return insertUserDao.insertUserWithGenPrimaryKey(user);
    }
}
