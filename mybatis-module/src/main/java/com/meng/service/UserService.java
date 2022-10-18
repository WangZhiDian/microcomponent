package com.meng.service;

import com.meng.domain.User;

public interface UserService {

    public User getUserById(Long id);

    int insertUser(User user);

    int updateUser(User user);

    int deleteUserById(Long id);

}
