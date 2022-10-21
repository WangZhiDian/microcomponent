package com.meng.dao;

import com.meng.domain.Score;
import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SelectUserDao {


    User getUserById(Long id);

    User getUserByNameAndAge(String name, int age);

    User getUserByNameAndAge2(@Param("name") String name, @Param("age") int age);

    User getUserByUser(User user);

    User getUserBySelective(User user);

    List<User> getUsersByName(User user);

    List<User> getUsersByMapInfo(Map map);

    int countByName(@Param("name") String name);

    Map<String, String> getUserToMap(@Param("id") Long id);

    List<Map<String, String>> getAllUserToMap();

    List<User> getUserByTable(@Param("tableName") String table);

    //----单表查询  end--------------------------------------------

    Score getScoreByUserAndCourse(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    List<Score> getScoreByCourse(@Param("course_id") Long courseId);

    Score getScoreByUserAndCourseAssociation(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    Score getScoreByUserAndCourseStepTwo(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    Score getScoreByUserAndCourseStepTwoMultiParam(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    User getUserByIdCollectionStepOne(@Param("user_id") Long userId);

    Score selectScoreByUserId(@Param("user_id") Long userId);

    User getUserByUserIdCollection(@Param("user_id") Long userId);

    User getUserByUserIdCollectionLazy(@Param("user_id") Long userId);

}
