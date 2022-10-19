package com.meng.service;

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

}
