package com.meng.dao;

import com.meng.domain.ScoreInfo;

public interface ScoreInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ScoreInfo record);

    int insertSelective(ScoreInfo record);

    ScoreInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoreInfo record);

    int updateByPrimaryKey(ScoreInfo record);
}