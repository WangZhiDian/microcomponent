package com.meng.dao;

import com.meng.domain.CourseInfo;

public interface CourseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CourseInfo record);

    int insertSelective(CourseInfo record);

    CourseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseInfo record);

    int updateByPrimaryKey(CourseInfo record);
}