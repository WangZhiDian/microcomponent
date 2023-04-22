package com.meng.es.dao.impl;

import com.meng.es.dao.IEsInfoDao;
import com.meng.es.domain.ObjPackageInfo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.meng.util.CommonUtils.copyBeanValue;


/**
 * spring data es info dao impl
 *
 * @author : sunyuecheng
 */
//@Repository("SpringDataEsInfoDao")
public class SpringDataEsInfoDaoImpl implements IEsInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDataEsInfoDaoImpl.class);

    private static final String WILDCARD_QUERY_FORMAT = "*%s*";

    private static final String INDEX_DATA_SUCCESS_RESULT_KEY = "HTTP/1.1 201 Created";

    /**
     * range query max value key
     */
    public static final String RANGE_QUERY_MAX_VALUE_KEY = "MAX_VALUE";

    /**
     * range query min value key
     */
    public static final String RANGE_QUERY_MIN_VALUE_KEY = "MIN_VALUE";

    @Autowired
    public ElasticsearchOperations elasticsearchOperations;

    @Override
    public <T> ObjPackageInfo<T> getObjPackageInfo(
            String indexName,
            Map<String, Object> termQueryParamMap,
            Map<String, String> wildcardQueryParamMap,
            Map<String, Map<String, Object>> rangeQueryParamMap,
            Map<String, Boolean> sortFieldNameMap,
            int index, int size,
            Class<T> resultClazz) {
        if (StringUtils.isEmpty(indexName) || termQueryParamMap == null || wildcardQueryParamMap == null
                || rangeQueryParamMap == null || sortFieldNameMap == null) {
            LOGGER.error("Invalid param.");
            return null;
        }

        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();

        termQueryParamMap.forEach((paramName, paramValue) -> {
            setTermQuery(childBoolQueryBuilder, paramName, paramValue);
        });

        wildcardQueryParamMap.forEach((paramName, paramValue) -> {
            setWildcardQuery(childBoolQueryBuilder, paramName, paramValue);
        });

        rangeQueryParamMap.forEach((paramName, paramValueMap) -> {
            Object maxValue = paramValueMap.get(RANGE_QUERY_MAX_VALUE_KEY);
            Object minValue = paramValueMap.get(RANGE_QUERY_MIN_VALUE_KEY);
            setRangeQuery(childBoolQueryBuilder, paramName, maxValue, minValue);
        });

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        sortFieldNameMap.forEach((sortFieldName, sortOrder) -> {
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortFieldName).order(
                    sortOrder ? SortOrder.DESC : SortOrder.ASC));
        });
        nativeSearchQueryBuilder.withPageable(PageRequest.of(index, size));

        SearchHits<T> searchHits = null;
        try {
            searchHits = elasticsearchOperations.search(nativeSearchQueryBuilder.build(),
                    resultClazz, IndexCoordinates.of(indexName));
        } catch (Exception e) {
            LOGGER.error("Get obj info error, index name({}), index({}), size({}), error({}).",
                    indexName, index, size, e.getMessage());
            return null;
        }

        List<T> dataList = new ArrayList<>();
        searchHits.getSearchHits().forEach(searchHit -> {
            dataList.add(copyBeanValue(searchHit.getContent(), resultClazz));
        });

        return new ObjPackageInfo((int) searchHits.getTotalHits(), dataList);
    }

    private void setWildcardQuery(BoolQueryBuilder childBoolQueryBuilder, String name, String value) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            return;
        }
        WildcardQueryBuilder matchBuilder = QueryBuilders.wildcardQuery(
                name, String.format(WILDCARD_QUERY_FORMAT, value));
        childBoolQueryBuilder.must(matchBuilder);
    }

    private void setTermQuery(BoolQueryBuilder childBoolQueryBuilder, String name, Object value) {
        if (StringUtils.isEmpty(name) || value == null) {
            return;
        }
        TermQueryBuilder matchBuilder = QueryBuilders.termQuery(name, value);
        childBoolQueryBuilder.must(matchBuilder);
    }

    private void setRangeQuery(BoolQueryBuilder childBoolQueryBuilder, String name, Object maxValue, Object minValue) {
        if (StringUtils.isEmpty(name) || maxValue == null || minValue == null) {
            return;
        }
        RangeQueryBuilder rangeQueryBuilder
                = QueryBuilders.rangeQuery(name).from(minValue).to(maxValue);
        childBoolQueryBuilder.must(rangeQueryBuilder);
    }

    @Override
    public <T> boolean saveObjInfo(String indexName, T obj) {
        if (StringUtils.isEmpty(indexName) || obj == null) {
            LOGGER.error("Invalid param.");
            return false;
        }

        IndexQueryBuilder indexQueryBuilder = new IndexQueryBuilder();
        try {
            String index = elasticsearchOperations.index(indexQueryBuilder.withObject(obj).build(),
                    IndexCoordinates.of(indexName));
        } catch (Exception e) {
            if(!e.getMessage().contains(INDEX_DATA_SUCCESS_RESULT_KEY)) {
                LOGGER.error("Save obj info error, index name({}), error({}).", indexName, e.getMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    public <T> boolean saveObjInfoList(String indexName, List<T> objList) {
        if (StringUtils.isEmpty(indexName) || objList == null || objList.isEmpty()) {
            LOGGER.error("Invalid param.");
            return false;
        }

        IndexQueryBuilder indexQueryBuilder = new IndexQueryBuilder();
        List<IndexQuery> indexQueryList = new ArrayList<>();
        objList.forEach(obj -> {
            indexQueryList.add(indexQueryBuilder.withObject(obj).build());
        });

        try {
            elasticsearchOperations.bulkIndex(indexQueryList, IndexCoordinates.of(indexName));
        } catch (Exception e) {
            LOGGER.error("Save obj info list error, index name({}), error({}).", indexName, e.getMessage());
            return false;
        }

        return true;
    }
}
