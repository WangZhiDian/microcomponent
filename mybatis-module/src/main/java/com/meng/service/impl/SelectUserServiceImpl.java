package com.meng.service.impl;

import com.meng.dao.SelectUserDao;
import com.meng.domain.User;
import com.meng.service.SelectUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class SelectUserServiceImpl implements SelectUserService {

    @Resource
    SelectUserDao selectUserDao;

    @Override
    public User getUserById(Long id) {

        return selectUserDao.getUserById(id);
    }

    @Override
    public User getUserByNameAndAge(String name, int age) {
        return selectUserDao.getUserByNameAndAge(name, age);
    }

    @Override
    public User getUserByNameAndAge2(String name, int age) {
        return selectUserDao.getUserByNameAndAge2(name, age);
    }

    @Override
    public User getUserByUser(User user) {
        return selectUserDao.getUserByUser(user);
    }

    @Override
    public User getUserBySelective(User user) {
        return selectUserDao.getUserBySelective(user);
    }

    @Override
    public List<User> getUsersByName(User user) {
        return selectUserDao.getUsersByName(user);
    }

    @Override
    public List<User> getUsersByMapInfo(Map map) {
        return selectUserDao.getUsersByMapInfo(map);
    }

    @Override
    public int countByName(String name) {
        return selectUserDao.countByName(name);
    }

    @Override
    public Map<String, String> getUserToMap(Long id) {
        return selectUserDao.getUserToMap(id);
    }

    @Override
    public List<Map<String, String>> getAllUserToMap() {
        return selectUserDao.getAllUserToMap();
    }
}
