package com.meng.service.impl;

import com.meng.dao.UpdateUserDao;
import com.meng.domain.User;
import com.meng.service.UpdateUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UpdateUserServiceImpl implements UpdateUserService {

    @Resource
    private UpdateUserDao updateUserDao;

    @Override
    public int updateUser(User user) {
        return updateUserDao.updateUser(user);
    }

    @Override
    public int batchUpdateUser(List<User> userList, int age) {
        return updateUserDao.batchUpdateUser(userList, age);
    }

    @Override
    public int batchUpdateUserMultiSql(List<User> userList) {
        return updateUserDao.batchUpdateUserMultiSql(userList);
    }

    @Override
    public int updateByCondition(Long id, String name) {
        return updateUserDao.updateByCondition(id, name);
    }

}
