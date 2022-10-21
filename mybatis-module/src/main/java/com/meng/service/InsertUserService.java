package com.meng.service;

import com.meng.domain.User;

public interface InsertUserService {


    int insertUserWithPrimaryKey(User user);

    int insertUserWithGenPrimaryKey(User user);

}
