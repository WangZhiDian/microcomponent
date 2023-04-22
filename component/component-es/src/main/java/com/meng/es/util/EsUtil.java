package com.meng.es.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.lang.reflect.Method;
import java.util.*;

/**
 * ElasticSearch 连接工具类
 *
 * @author porty
 * @date 2021年4月28日10:40:36
 */
@SuppressWarnings("all")
public class EsUtil {
    /**
     * 客户端,本次使用本地连接
     */
    public static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")));
    ;

    /**
     * 默认类型
     */
    public static final String DEFAULT_TYPE = "_doc";

    /**
     * set方法前缀
     */
    public static final String SET_METHOD_PREFIX = "set";

    /**
     * 返回状态-CREATED
     */
    public static final String RESPONSE_STATUS_CREATED = "CREATED";

    /**
     * 返回状态-OK
     */
    public static final String RESPONSE_STATUS_OK = "OK";

    /**
     * 返回状态-NOT_FOUND
     */
    public static final String RESPONSE_STATUS_NOT_FOUND = "NOT_FOUND";

    /**
     * 需要过滤的文档数据
     */
    public static final String[] IGNORE_KEY = {"@timestamp", "@version","type"};

    /**
     * 超时时间 1s
     */
    public static final TimeValue TIME_VALUE_SECONDS = TimeValue.timeValueSeconds(1);

    /**
     * 批量新增
     */
    public static final String PATCH_OP_TYPE_INSERT = "insert";

    /**
     * 批量删除
     */
    public static final String PATCH_OP_TYPE_DELETE = "delete";

    /**
     * 批量更新
     */
    public static final String PATCH_OP_TYPE_UPDATE = "update";

    /**
     * 剔除指定文档数据,减少不必要的循环
     *
     * @param map 文档数据
     */
    private static void ignoreSource(Map<String, Object> map) {
        for (String key : IGNORE_KEY) {
            map.remove(key);
        }
    }

    /**
     * 将文档数据转化为指定对象
     *
     * @param sourceAsMap 文档数据
     * @param clazz       转换目标Class对象
     * @return
     */
    private static <T> T dealObject(Map<String, Object> sourceAsMap, Class<T> clazz) {
        try {
            ignoreSource(sourceAsMap);
            Iterator<String> keyIterator = sourceAsMap.keySet().iterator();
            T t = clazz.newInstance();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                String replaceKey = key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                Method method = null;
                try {
                    method = clazz.getMethod(SET_METHOD_PREFIX + replaceKey, sourceAsMap.get(key).getClass());
                }catch (NoSuchMethodException e) {
                    continue;
                }
                method.invoke(t, sourceAsMap.get(key));
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定文档是否存在
     *
     * @param index 索引
     * @param id    文档id
     */
    public static boolean isExists(String index, String id) {
        return isExists(index, DEFAULT_TYPE, id);
    }

    /**
     * 指定文档是否存在
     *
     * @param index 索引
     * @param type  类型
     * @param id    文档id
     */
    public static boolean isExists(String index, String type, String id) {
        GetRequest request = new GetRequest(index, type, id);
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            return response.isExists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据id查询文档
     *
     * @param index 索引
     * @param id    文档id
     * @param clazz 转换目标Class对象
     * @return 对象
     */
    public static <T> T selectDocumentById(String index, String id, Class<T> clazz) {
        return selectDocumentById(index, DEFAULT_TYPE, id, clazz);
    }

    /**
     * 根据id查询文档
     *
     * @param index 索引
     * @param type  类型
     * @param id    文档id
     * @param clazz 转换目标Class对象
     * @return 对象
     */
    public static <T> T selectDocumentById(String index, String type, String id, Class<T> clazz) {
        try {
            type = type == null || type.equals("") ? DEFAULT_TYPE : type;
            GetRequest request = new GetRequest(index, type, id);
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                Map<String, Object> sourceAsMap = response.getSourceAsMap();
                return dealObject(sourceAsMap, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * （筛选条件）获取数据集合
     *
     * @param index         索引
     * @param sourceBuilder 请求条件
     * @param clazz         转换目标Class对象
     */
    public static <T> List<T> selectDocumentList(String index, SearchSourceBuilder sourceBuilder, Class<T> clazz) {
        try {
            SearchRequest request = new SearchRequest(index);
            if (sourceBuilder != null) {
                // 返回实际命中数
                sourceBuilder.trackTotalHits(true);
                request.source(sourceBuilder);
            }
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            if (response.getHits() != null) {
                List<T> list = new ArrayList<>();
                SearchHit[] hits = response.getHits().getHits();
                for (SearchHit documentFields : hits) {
                    Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
                    list.add(dealObject(sourceAsMap, clazz));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增/修改文档信息
     *
     * @param index 索引
     * @param id    文档id
     * @param data  数据
     */
    public static boolean insertDocument(String index, String id, Object data) {
        try {
            IndexRequest request = new IndexRequest(index);
            request.timeout(TIME_VALUE_SECONDS);
            if (id != null && id.length() > 0) {
                request.id(id);
            }
            request.source(JSON.toJSONString(data), XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            String status = response.status().toString();
            if (RESPONSE_STATUS_CREATED.equals(status) || RESPONSE_STATUS_OK.equals(status)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新文档信息
     *
     * @param index 索引
     * @param id    文档id
     * @param data  数据
     */
/*    public static boolean updateDocument(String index, String id, Object data) {
        try {
            UpdateRequest request = new UpdateRequest(index, id);
            request.doc(data, XContentType.JSON);
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            String status = response.status().toString();
            if (RESPONSE_STATUS_OK.equals(status)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /**
     * 删除文档信息
     *
     * @param index 索引
     * @param id    文档id
     */
/*
    public static boolean deleteDocument(String index, String id) {
        try {
            DeleteRequest request = new DeleteRequest(index, id);
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            String status = response.status().toString();
            if (RESPONSE_STATUS_OK.equals(status)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
*/

    /**
     * 批量操作
     *
     * @param index    索引
     * @param opType   操作类型 PATCH_OP_TYPE_*
     * @param dataList 数据集 新增修改需要传递
     * @param timeout  超时时间 单位为秒
     */
    public static boolean patch(String index, String opType, List<Object> dataList, long timeout) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout(TimeValue.timeValueSeconds(timeout));
            if (dataList != null && dataList.size() > 0) {
                if (PATCH_OP_TYPE_INSERT.equals(opType)) {
                    for (Object obj : dataList) {
                        bulkRequest.add(
                                new IndexRequest("index")
                                        .source(JSON.toJSONString(obj), XContentType.JSON)
                        );
                    }
                } else if (PATCH_OP_TYPE_DELETE.equals(opType)) {
                    //  待补充
                } else if (PATCH_OP_TYPE_UPDATE.equals(opType)) {
                    //  待补充
                } else {
                    return false;
                }
                BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                if (!response.hasFailures()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}


