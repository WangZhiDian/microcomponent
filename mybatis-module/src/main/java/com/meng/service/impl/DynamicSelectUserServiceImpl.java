package com.meng.service.impl;

import com.meng.dao.DylamicSelectUserDao;
import com.meng.domain.User;
import com.meng.service.DynamicSelectUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DynamicSelectUserServiceImpl implements DynamicSelectUserService {


    @Resource
    DylamicSelectUserDao dylamicSelectUserDao;

    @Override
    public List<User> getUserByConditionIf(User user) {
        return dylamicSelectUserDao.getUserByConditionIf(user);
    }

    @Override
    public List<User> getUserByConditionWhere(User user) {
        return dylamicSelectUserDao.getUserByConditionWhere(user);
    }

    @Override
    public List<User> getUserByConditionTrim(User user) {
        return dylamicSelectUserDao.getUserByConditionTrim(user);
    }

    @Override
    public List<User> getUserByConditionChoose(User user) {
        return dylamicSelectUserDao.getUserByConditionChoose(user);
    }

    @Override
    public List<User> getUserByForeachIds(List<Long> userIdList) {
        return dylamicSelectUserDao.getUserByForeachIds(userIdList);
    }

    @Override
    public List<User> getUserByForeachUsers(List<User> users) {
        return dylamicSelectUserDao.getUserByForeachUsers(users);
    }
}
