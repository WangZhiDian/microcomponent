package com.meng.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meng.dao.SelectUserDao;
import com.meng.domain.Score;
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

    @Override
    public List<User> getUserByTable(String table) {
        return selectUserDao.getUserByTable(table);
    }

    @Override
    public Score getScoreByUserAndCourse(Long userId, Long courseId) {
        return selectUserDao.getScoreByUserAndCourse(userId, courseId);
    }

    @Override
    public List<Score> getScoreByCourse(Long courseId) {
        return selectUserDao.getScoreByCourse(courseId);
    }

    @Override
    public Score getScoreByUserAndCourseAssociation(Long userId, Long courseId) {
        return selectUserDao.getScoreByUserAndCourseAssociation(userId, courseId);
    }

    @Override
    public Score getScoreByUserAndCourseStepTwo(Long userId, Long courseId) {
        return selectUserDao.getScoreByUserAndCourseStepTwo(userId, courseId);
    }

    @Override
    public Score getScoreByUserAndCourseStepTwoMultiParam(Long userId, Long courseId) {
        return selectUserDao.getScoreByUserAndCourseStepTwoMultiParam(userId, courseId);
    }

    @Override
    public User getUserByIdCollectionStepOne(Long userId) {
        return selectUserDao.getUserByIdCollectionStepOne(userId);
    }

    @Override
    public Score selectScoreByUserId(Long userId) {
        return selectUserDao.selectScoreByUserId(userId);
    }

    @Override
    public User getUserByUserIdCollection(Long userId) {
        return selectUserDao.getUserByUserIdCollection(userId);
    }

    @Override
    public User getUserByUserIdCollectionLazy(Long userId) {
        User user = selectUserDao.getUserByUserIdCollectionLazy(userId);
        System.out.println("延迟加载查询中===========" + user.getName());
        System.out.println("scores:" + JSON.toJSONString(user.getScoreList()));
        return user;
    }
}
