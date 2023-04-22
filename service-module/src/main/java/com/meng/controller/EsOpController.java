package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.es.dao.IEsInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**

 */
@Slf4j
@RestController
public class EsOpController {

    //@Resource(name = "SpringDataEsInfoDao")
    IEsInfoDao iEsInfoDao;

    @GetMapping(value = "/es/put")
    public ApiResult deal(@RequestParam("index") String index,
                          @RequestParam("value") String value) throws ExecutionException, InterruptedException, TimeoutException {

        iEsInfoDao.saveObjInfo(index, value);

        return ApiResult.success("success");
    }



}
