package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.es.dao.IEsInfoDao;
import com.meng.minio.util.MinioClientUtil;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**

 */
@Slf4j
@RestController
public class MinioOpController {

    @Autowired
    MinioClientUtil minioClientUtil;

    @GetMapping(value = "/minio/exist")
    public ApiResult deal(@RequestParam("bucket") String bucket,
                          @RequestParam("filename") String filename) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        boolean flag = minioClientUtil.bucketExists(bucket);
        return ApiResult.success("exiest: " + flag);
    }

    @GetMapping(value = "/minio/file")
    public void deal2(@RequestParam("bucket") String bucket,
                      @RequestParam("filename") String filename,
                      HttpServletResponse response) {
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
        minioClientUtil.getObject(bucket, filename, response);
    }

    @PostMapping(value = "/minio/file")
    public void upload(@RequestParam("multipartFile") MultipartFile multipartFile,
                       @RequestParam("bucket") String bucket,
                       @RequestParam("filename") String filename) {
        log.info("bucket:{}", bucket);
        minioClientUtil.putObject(bucket, multipartFile, filename);
    }


}
