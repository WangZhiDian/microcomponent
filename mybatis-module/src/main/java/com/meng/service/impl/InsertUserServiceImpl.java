package com.meng.service.impl;

import com.meng.dao.InsertUserDao;
import com.meng.domain.User;
import com.meng.service.InsertUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class InsertUserServiceImpl implements InsertUserService {


    @Resource
    private InsertUserDao insertUserDao;

    @Override
    public int insertUserWithPrimaryKey(User user) {
        return insertUserDao.insertUserWithPrimaryKey(user);
    }

    @Override
    public int insertUserWithGenPrimaryKey(User user) {
        return insertUserDao.insertUserWithGenPrimaryKey(user);
    }

    @Override
    public int replaceIntoUserValues(User user) {
        return insertUserDao.replaceIntoUserValues(user);
    }

    @Override
    public int replaceIntoUserSet(User user) {
        return insertUserDao.replaceIntoUserSet(user);
    }

    @Override
    public int batchReplaceIntoUser(List<User> userList) {
        return insertUserDao.batchReplaceIntoUser(userList);
    }

    @Override
    public int insertUserOnDuplicate(User user) {
        return insertUserDao.insertUserOnDuplicate(user);
    }

    @Override
    public int batchInsertUserOneSql(List<User> userList) {
        return insertUserDao.batchInsertUserOneSql(userList);
    }

    @Override
    public int batchInsertUserMultiSql(List<User> userList, int age) {
        return insertUserDao.batchInsertUserMultiSql(userList, age);
    }
}
