package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.Score;
import com.meng.domain.User;
import com.meng.service.SelectUserService;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SelectUserController {

    @Autowired
    SelectUserService selectUserService;


    @GetMapping(value = "/select/getUserByID")
    public String getUserById(@Param("id") Long id) {

        User user = selectUserService.getUserById(id);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @GetMapping(value = "/select/getUserByNameAndAge")
    public String getUserByNameAndAge(@Param("name") String name, @Param("age") int age) {

        User user = selectUserService.getUserByNameAndAge(name, age);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @GetMapping(value = "/select/getUserByNameAndAge2")
    public String getUserByNameAndAge2(@Param("name") String name, @Param("age") int age) {

        User user = selectUserService.getUserByNameAndAge2(name, age);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @GetMapping(value = "/select/getUserByUser")
    public String getUserByUser(@Param("id") Long id, @Param("name") String name,
                                @Param("age") int age) {

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        User userRet = selectUserService.getUserByUser(user);
        String ret = JSONObject.toJSONString(userRet);
        return ret;
    }

    @GetMapping(value = "/select/getUserBySelective")
    public String getUserBySelective(@Param("id") Long id) {

        User user = new User();
        user.setId(id);
        User user2 = selectUserService.getUserBySelective(user);
        String ret = JSONObject.toJSONString(user2);
        return ret;
    }

    @GetMapping(value = "/select/getUsersByName")
    public String getUsersByName(@Param("name") String name) {
        User user = new User();
        user.setName(name);
        List<User> userList = selectUserService.getUsersByName(user);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/select/getUsersByMapInfo")
    public String getUsersByMapInfo(@Param("name") String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        List<User> userList = selectUserService.getUsersByMapInfo(map);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/select/countByName")
    public int countByName(@Param("name") String name) {
        int ret = selectUserService.countByName(name);
        return ret;
    }

    @GetMapping(value = "/select/getUserToMap")
    public String getUserToMap(@Param("id") Long id) {
        Map map = selectUserService.getUserToMap(id);
        String ret = JSONObject.toJSONString(map);
        return ret;
    }

    @GetMapping(value = "/select/getAllUserToMap")
    public String getAllUserToMap() {
        List<Map<String, String>> mapList = selectUserService.getAllUserToMap();
        String ret = JSONObject.toJSONString(mapList);
        return ret;
    }

    @GetMapping(value = "/select/getUserByTable")
    public String getUserByTable(@Param("tableName") String tableName) {
        List<User> userList = selectUserService.getUserByTable(tableName);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    //---多表查询--------------------------------------------------------------------

    @GetMapping(value = "/select/getScoreByUserAndCourse")
    public String getScoreByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId) {
        Score score = selectUserService.getScoreByUserAndCourse(userId, courseId);
        String ret = JSONObject.toJSONString(score);
        return ret;
    }

    @GetMapping(value = "/select/getScoreByCourse")
    public String getScoreByCourse(@Param("courseId") Long courseId) {
        List<Score> scoreList = selectUserService.getScoreByCourse(courseId);
        String ret = JSONObject.toJSONString(scoreList);
        return ret;
    }

    @GetMapping(value = "/select/getScoreByUserAndCourseAssociation")
    public String getScoreByUserAndCourseAssociation(@Param("userId") Long userId, @Param("courseId") Long courseId) {
        Score score = selectUserService.getScoreByUserAndCourseAssociation(userId, courseId);
        String ret = JSONObject.toJSONString(score);
        return ret;
    }

    @GetMapping(value = "/select/getScoreByUserAndCourseStepTwo")
    public String getScoreByUserAndCourseStepTwo(@Param("userId") Long userId, @Param("courseId") Long courseId) {
        Score score = selectUserService.getScoreByUserAndCourseStepTwo(userId, courseId);
        String ret = JSONObject.toJSONString(score);
        return ret;
    }

    @GetMapping(value = "/select/getScoreByUserAndCourseStepTwoMultiParam")
    public String getScoreByUserAndCourseStepTwoMultiParam(@Param("userId") Long userId, @Param("courseId") Long courseId) {
        Score score = selectUserService.getScoreByUserAndCourseStepTwoMultiParam(userId, courseId);
        String ret = JSONObject.toJSONString(score);
        return ret;
    }

    @GetMapping(value = "/select/getUserByIdCollectionStepOne")
    public String getUserByIdCollectionStepOne(@Param("userId") Long userId) {
        User user = selectUserService.getUserByIdCollectionStepOne(userId);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @GetMapping(value = "/select/getUserByUserIdCollection")
    public String getUserByUserIdCollection(@Param("userId") Long userId) {
        User user = selectUserService.getUserByUserIdCollection(userId);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @GetMapping(value = "/select/getUserByUserIdCollectionLazy")
    public String getUserByUserIdCollectionLazy(@Param("userId") Long userId) {
        User user = selectUserService.getUserByUserIdCollectionLazy(userId);
        System.out.println("===" + user.getScoreList());
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

}
