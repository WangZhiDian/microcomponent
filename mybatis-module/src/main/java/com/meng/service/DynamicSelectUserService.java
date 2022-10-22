package com.meng.service;

import com.meng.domain.User;

import java.util.List;

public interface DynamicSelectUserService {


    List<User> getUserByConditionIf(User user);

    List<User> getUserByConditionWhere(User user);

    List<User> getUserByConditionTrim(User user);

    List<User> getUserByConditionChoose(User user);

    List<User> getUserByForeachIds(List<Long> userIdList);

    List<User> getUserByForeachUsers(List<User> users);


}
