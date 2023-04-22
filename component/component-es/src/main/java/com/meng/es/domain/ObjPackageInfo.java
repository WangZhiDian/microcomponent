package com.meng.es.domain;

import java.util.List;

/**
 * obj package info
 *
 * @author : sunyuecheng
 */
public class ObjPackageInfo<E> {
    private Integer totalNum;

    private List<E> dataList;

    /**
     * obj package info
     *
     * @param totalNum :
     * @param dataList :
     */
    public ObjPackageInfo(Integer totalNum, List<E> dataList) {
        this.totalNum = totalNum;
        this.dataList = dataList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }
}
