package com.meng.es.dao;


import com.meng.es.domain.ObjPackageInfo;

import java.util.List;
import java.util.Map;

/**
 * es info dao
 *
 * @author : sunyuecheng
 */
public interface IEsInfoDao {

    /**
     * get obj package info
     *
     * @param indexName             :
     * @param termQueryParamMap     :
     * @param wildcardQueryParamMap :
     * @param rangeQueryParamMap    :
     * @param sortFieldNameMap      :
     * @param index                 :
     * @param size                  :
     * @param resultClazz           :
     * @return ObjPackageInfo<T> :
     */
    <T> ObjPackageInfo<T> getObjPackageInfo(String indexName,
                                            Map<String, Object> termQueryParamMap,
                                            Map<String, String> wildcardQueryParamMap,
                                            Map<String, Map<String, Object>> rangeQueryParamMap,
                                            Map<String, Boolean> sortFieldNameMap,
                                            int index, int size,
                                            Class<T> resultClazz);

    /**
     * save obj info
     *
     * @param indexName :
     * @param obj       :
     * @return boolean :
     */
    <T> boolean saveObjInfo(String indexName, T obj);

    /**
     * save obj info list
     *
     * @param indexName :
     * @param objList :
     * @return boolean :
     */
    <T> boolean saveObjInfoList(String indexName, List<T> objList);
}
