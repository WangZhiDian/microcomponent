package com.meng.service.impl;

import com.meng.dao.DeleteUserDao;
import com.meng.domain.User;
import com.meng.service.DeleteUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DeleteUserServiceImpl implements DeleteUserService {

    @Resource
    DeleteUserDao deleteUserDao;

    @Override
    public int deleteUserById(Long id) {
        return deleteUserDao.deleteUserById(id);
    }

    @Override
    public int batchDeleteByIds(List<Long> userIds) {
        return deleteUserDao.batchDeleteByIds(userIds);
    }

    @Override
    public int batchDeleteByUsers(List<User> userList) {
        return deleteUserDao.batchDeleteByUsers(userList);
    }
}
