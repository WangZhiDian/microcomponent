package com.meng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meng.bean.ApiResult;
import com.meng.bean.DataTransObj;
import com.meng.redis.dao.IRedisInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 完成远程http和https的访问
 */
@Slf4j
@RestController
public class HttpOpController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IRedisInfoDao iRedisInfoDao;

    @GetMapping(value = "/rest/get")
    public ApiResult<Object> dealGet(@RequestParam("key") String key) {
        String uri = "http://localhost:8080/redis/get?key=namewang";

        //方式一 get请求------------------------------
        String ret1 = getForObject(uri, key);
        String ret4 = getForObjectWithHeader(uri, key);
        //方式二 get请求------------------------------
        String ret2 = getForEntity(uri, key);
        String ret3 = getForExchange(uri, key);
        return ApiResult.success("suc");
    }

    private String getForObject(String uri, String key) {
        HashMap<String, Object> uriParams = new HashMap<>();//空参
        uriParams.put("key1", key);
        ApiResult value = restTemplate.getForObject(uri, ApiResult.class, uriParams);
        return value.getData().toString();
    }

    /**
     * header设置可能会异常，在获取的时候，能直接获取
     */
    private String getForObjectWithHeader(String uri, String key) {
        //header类型
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", "mytoken-info");
        //JSON String
        String param = "{\"key\":\"namewang\"}";
        HttpEntity<String> httpEntity = new HttpEntity<>(param, httpHeaders);
        ApiResult response = restTemplate.getForObject(uri, ApiResult.class, httpEntity);

        return response.getData().toString();
    }

    private String getForEntity(String uri, String key) {
        HashMap<String,Object> uriParams = new HashMap<>();
        uriParams.put("key",key);
        uriParams.put("count",2);
        ResponseEntity<String> responseEntity
                = restTemplate.getForEntity(uri, String.class, uriParams);
        int status = responseEntity.getStatusCode().value();
        log.info("status:{}", status);
        String body = responseEntity.getBody();
        System.out.println(body);
        ApiResult apiResult = JSONObject.parseObject(body, ApiResult.class);

        return apiResult.toString();
    }

    /**
     * 该方法是通用的请求方式，支持 GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE，
     * 当上面的方式不能满足你可采用该方式定制，该方式提供了更加灵活的 API，比如你可以定制 GET 方法的请求头，
     * 放入 Jwt Token等操作，这是getForObject 无法比拟的。
     */
    private String getForExchange(String uri, String key) {
        //add Header
        HttpHeaders headers = new HttpHeaders();
        //添加接收数据媒体类型,JSON
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(acceptableMediaTypes);
        headers.add("token", "tokens");

        //创建请求体
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
        //发出请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );
        //获取返回值
        String body = responseEntity .getBody();
        //获取响应类型
        MediaType contentType = responseEntity .getHeaders().getContentType();
        //获取响应状态码
        HttpStatus statusCode = responseEntity .getStatusCode();
        return "";
    }


    @GetMapping(value = "/rest/post")
    public ApiResult<Object> dealPost(@RequestParam("key") String key,
                                      @RequestParam("value") String value) {
        String uri = "http://localhost:8080/redis/post";

        postForObject(uri, key, value);
        postForExchange(uri, key, value);

        return ApiResult.success(value);
    }

    private void postForObject(String uri, String key, String value) {
        DataTransObj dataTransObj = new DataTransObj(key, value);
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "tokens");
        headers.add("Content-Type", "application/json");
        String body = JSON.toJSONString(dataTransObj);
        HttpEntity<String> entity = new HttpEntity(body, headers);
        String response = restTemplate.postForObject(uri, entity, String.class);
        log.info("response:{}", response);
        // 获取entity实体的方法，可以获取post结果的status
        ResponseEntity<String> responseEntity
                = restTemplate.postForEntity(uri, entity, String.class);
        //获取返回值
        String body2 = responseEntity.getBody();
        //获取响应类型
        MediaType contentType = responseEntity.getHeaders().getContentType();
        //获取响应状态码
        HttpStatus statusCode = responseEntity.getStatusCode();
        log.info("status:{}", statusCode);
    }

    private void postForExchange(String uri, String key, String value) {
        DataTransObj dataTransObj
                = new DataTransObj(key, value);
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "tokens");
        headers.add("Content-Type", "application/json");

        String body = JSON.toJSONString(dataTransObj);
        HttpEntity<String> entity = new HttpEntity(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        //获取返回值
        String body2 = responseEntity.getBody();
        //获取响应类型
        MediaType contentType = responseEntity.getHeaders().getContentType();
        //获取响应状态码
        HttpStatus statusCode = responseEntity.getStatusCode();
        log.info("status:{}", statusCode);
    }

}
