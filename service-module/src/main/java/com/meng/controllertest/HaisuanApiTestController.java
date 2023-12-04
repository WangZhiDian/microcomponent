package com.meng.controllertest;

import com.meng.bean.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;

import java.util.Random;

import java.util.TreeMap;

import org.springframework.web.client.RestTemplate;



/**
 * 描述总结：
 */
@Slf4j
@RestController
public class HaisuanApiTestController {

    private static long http_timeout = 3;// 超时30秒

    private static String AppKey = "af78d4aa06e34ff6ab7d612b82369bd4";

    private static String AppSecret = "7ed7f434241d77823ab87a54656aa49b";

    @Autowired
    RestTemplate restTemplate;

    /**
     * 说用：
     */
    @GetMapping(value = "/haisuan/{info}")
    public ApiResult<Object> deal(@PathVariable("info") String info) throws InterruptedException {


        String spiPath = "http://192.168.133.28:45180/outsidegateway/w4rd7lu36659i7h1/homeCommonMyself/homeResourceAppliInfo";

        HashMap<String, String> map = new HashMap();
        String uri = spiPath + "?" + getQueryUrl(map);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("hsPartyId", "hl230100");
        //JSON String
        //String param = "{\"hsPartyId\":\"hl230100\"}";
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);


        //String forObject = restTemplate.getForObject(uri, String.class, httpEntity);
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        //restTemplate.getForOb
        return ApiResult.success("suc");
    }

    /**
     * 测试库表转接口的验证
     * */
    @GetMapping(value = "/haisuan/kubo-i")
    public ApiResult<Object> deal2() throws InterruptedException {


        String spiPath = "http://192.168.133.28:45180/outsidegateway/71ud6m24s41a525y/a0ad7958e7e544d6876a2b5de4c21ae0/v1";
        spiPath = "http://192.168.133.28:45180/outsidegateway/a0ad7958e7e544d6876a2b5de4c21ae0/v1";



        HashMap<String, String> map = new HashMap();
        map.put("pageSize", 3 + "");
        map.put("pageIndex", 0 + "");
        String uri = spiPath + "?" + getQueryUrl(map);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("hsPartyId", "hl230100");
        //JSON String
        //String param = "{\"hsPartyId\":\"hl230100\"}";
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);


        //String forObject = restTemplate.getForObject(uri, String.class, httpEntity);
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        //restTemplate.getForOb
        return ApiResult.success("suc");
    }


    /**

    * 获得query参数，没有传空集合

    *

    * @param params

    * @return

    */
    public static String getQueryUrl(Map<String, String> params) {

        Map<String, String> sorted = new TreeMap<>();

        if (null != params && params.size() > 0) {

            params.forEach((k, v) -> sorted.put(k, v));

        }

        // 海算平台上创建应用的AppKey
        sorted.put("AccessKey",AppKey);

        // 海算平台上创建应用的AppSecret
        sorted.put("SecretKey", AppSecret);

        // 时间戳
        sorted.put("timestamp", String.valueOf(System.currentTimeMillis()));

        Random random = new Random();
        // 随机数
        sorted.put("nonce", String.valueOf(random.nextInt(1)));

        // Query位置的业务参数也需要纳入计算

        // sorted.put("xxx", “xxx”);

        // 最后计算签名
        sorted.put("sign", getSign(sorted));

        sorted.remove("SecretKey");

        StringBuilder builder = new StringBuilder();

        sorted.forEach((k, v) -> {

            try {

                builder.append(k).append("=").append(URLEncoder.encode(v, "utf-8")).append("&");

            } catch (UnsupportedEncodingException e) {

                System.out.println("对参数进行重新编码时出错。");

            }

        });

        builder.deleteCharAt(builder.length()-1);

        return builder.toString();

    }

    //计算sign

    public static String getSign(Map<String, String> params) {

        Map<String, String> sorted = new TreeMap<>();

        params.forEach((k, v) -> sorted.put(k, v));

        StringBuilder builder = new StringBuilder();

        sorted.forEach((k, v) -> {

            builder.append(k).append("=").append(v).append("&");

        });

        String value = builder.toString();

        value = value.substring(0, value.length() - 1);

        return DigestUtils.md5DigestAsHex(value.getBytes()).toUpperCase();

    }


}
