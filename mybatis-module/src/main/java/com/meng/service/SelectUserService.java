package com.meng.service;

import com.meng.domain.Score;
import com.meng.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SelectUserService {

    User getUserById(Long id);

    /**
     * 可以通过@Param注解标识mapper接口中的方法参数，此时，会将这些参数放在map集合中
     * 以@Param注解的value属性值为键，以参数为值；
     * 以param1,param2…为键，以参数为值；
     * 只需要通过${}和#{}访问map集合的键就可以获取相对应的值，注意${}需要手动加单引号
     * */
    User getUserByNameAndAge(String name, int age);

    User getUserByNameAndAge2(@Param("name") String name, @Param("age") int age);

    User getUserByUser(User user);

    User getUserBySelective(User user);

    List<User> getUsersByName(User user);

    List<User> getUsersByMapInfo(Map map);

    /**
     * 在MyBatis中，对于Java中常用的类型都设置了类型别名
     * 例如：java.lang.Integer-->int|integer
     * 例如：int-->_int|_integer
     * 例如：Map-->map,List-->list
     */
    int countByName(@Param("name") String name);

    /**
     * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，此时可以将这些map放在一个list集合中获取
     */
    Map<String, String> getUserToMap(@Param("id") Long id);

    List<Map<String, String>> getAllUserToMap();

    List<User> getUserByTable(@Param("tableName") String table);

    //-----单表查询 end -------------------------------------------------------------
    /**
     * 使用集联的方式，查询表数据，但数据和列表数据
     * */
    Score getScoreByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    List<Score> getScoreByCourse(@Param("courseId") Long courseId);


    /**
     * association 处理映射关系，并且使用联合查询， 主要处理多对一
     * association：处理多对一的映射关系
     * property：需要处理多对的映射关系的属性名
     * javaType：该属性的类型
     * */
    Score getScoreByUserAndCourseAssociation(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    /**
     *  association 处理映射关系，分步查询， 主要处理多一
     * select：设置分布查询的sql的唯一标识（namespace.SQLId或mapper接口的全类名.方法名）
     * column：设置分步查询的条件
     */
    Score getScoreByUserAndCourseStepTwo(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    /**
     *  association 处理映射关系，分步查询， 主要处理多一
     * select：设置分布查询的sql的唯一标识（namespace.SQLId或mapper接口的全类名.方法名）
     * column：设置分步查询的条件,可以传递多个参数给二级查询
     */
    Score getScoreByUserAndCourseStepTwoMultiParam(@Param("user_id") Long userId, @Param("course_id") Long courseId);

    /**
     * collection 联合查询， 处理一对多
     * collection：用来处理一对多的映射关系
     * ofType：表示该属性对饮的集合中存储的数据的类型
     */
    User getUserByIdCollectionStepOne(@Param("user_id") Long userId);

    Score selectScoreByUserId(@Param("user_id") Long userId);

    /**
     * collection 分步查询， 处理一对多
     * collection：用来处理一对多的映射关系
     */
    User getUserByUserIdCollection(@Param("user_id") Long userId);

    /**
     * collection 分步查询， 处理一对多
     * collection：用来处理一对多的映射关系
     * 二级查询添加延迟参数：fetchType:Lazy
     */
    User getUserByUserIdCollectionLazy(@Param("user_id") Long userId);












}
