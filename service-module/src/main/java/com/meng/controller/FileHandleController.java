package com.meng.controller;


import cn.hutool.core.io.FileUtil;
import com.meng.bean.ApiResult;
import com.meng.bean.MinioResponseDTO;
import com.meng.config.MinioConfig;
import com.meng.config.MinioProp;
import com.meng.util.MinioClientUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fileHandle")
@Slf4j
@AllArgsConstructor
public class FileHandleController {


    @Autowired
    private MinioClientUtils minioClientUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioProp minioProp;

    @PostMapping(value = {"/admin/uploadFile","/web/uploadFile"})
    @ResponseBody
    //@ApiOperation(value = "上传文件,支持批量上传")
    //@ApiImplicitParam(name = "files",value = "文件对象",dataType = "File")
    public ApiResult uploadFile(@RequestParam("files") List<MultipartFile> files) {
        log.info(files.toString());
        if (CollectionUtils.isEmpty(files)){
            return ApiResult.error("未选择文件！");
        }

        List<MinioResponseDTO> MinioResponseDTOList=new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
//            获取文件拓展名
            String extName = FileUtil.extName(originalFilename);
            log.info("文件拓展名:"+extName);
//            生成新的文件名，存入到minio
            long millSeconds = Instant.now().toEpochMilli();
            String minioFileName=millSeconds+ RandomStringUtils.randomNumeric(12)+"."+extName;
            String contentType = file.getContentType();
            log.info("文件mime:{}",contentType);
//            返回文件大小,单位字节
            long size = file.getSize();
            log.info("文件大小："+size);
            try {
                String bucketName = minioProp.getBucketName();
                minioClientUtils.putObject(bucketName,file,minioFileName);
                String fileUrl = minioClientUtils.getObjectUrl(bucketName, minioFileName);
                /*MinioFile minioFile = new MinioFile();
                minioFile.setOriginalFileName(originalFilename);
                minioFile.setFileExtName(extName);
                minioFile.setFileName(minioFileName);
                minioFile.setFileSize(size);
                minioFile.setMime(contentType);
                minioFile.setIsDelete(NumberUtils.INTEGER_ZERO);
                minioFile.setFileUrl(fileUrl);
                boolean insert = minioFile.insert();
                if (insert) {
                    MinioResponseDTO minioResponseDTO = new MinioResponseDTO();
                    minioResponseDTO.setFileId(minioFile.getId());
                    minioResponseDTO.setOriginalFileName(originalFilename);
                    minioResponseDTO.setFileUrl(fileUrl);
                    MinioResponseDTOList.add(minioResponseDTO);
                }*/



            } catch (Exception e) {
                log.error("上传文件出错:{}",e);
                return ApiResult.error("上传文件出错");

            }
        }

        return ApiResult.success("");
    }


    /**
     * 仅仅用于测试，是否可以正常上传文件
     * @return
     * @throws Exception
     */
    @GetMapping("/test")
    //@ApiOperation(value = "测试minio文件上传")
    public ApiResult testPutObject() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\MSI\\Desktop\\新建文本文档.txt");
        boolean bs = minioClientUtils.putObject("fsp-dev", "新建文本文档.txt", fileInputStream, "image/jpg");
        log.info("上传成功?"+bs);
        return ApiResult.success("上传成功");
    }

    /**
     * 仅仅用于测试，是否可以获取桶
     * @return
     * @throws Exception
     */
    @GetMapping("/buckets/list")
    //@ApiOperation(value = "测试minio文件上传")
    public ApiResult listPutObject() throws Exception {
        List<String> buckets = minioClientUtils.listBucketNames();
        return ApiResult.success(buckets);
    }

    @GetMapping("/buckets/video")
    //@ApiOperation(value = "测试minio文件上传")
    public ApiResult getVideoInfo() throws Exception {
        List<String> buckets = minioClientUtils.listBucketNames();

        return ApiResult.success(buckets);
    }

    //@ApiOperation(value = "minio", notes = "获取文件流")
    @GetMapping(value = "/minio/video")
    void downloadStream(@RequestParam(value = "bucket") String bucket,
                        @RequestParam(value = "object") String object,
                        HttpServletResponse response) throws Exception {

        //获取视频文件流
        InputStream inputStream = null;
       // OutputStream outputStream = response.getOutputStream();
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

        try {
            inputStream = minioClientUtils.getObject(bucket, object);
            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = inputStream.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            inputStream.close();
        } catch (Exception bd) {
            log.error("BaseException:", bd);
            response.sendError(500, bd.getMessage());
        }
    }

    @GetMapping(value = "/minio/video2")
    void downloadStream2(@RequestParam(value = "bucket") String bucket,
                        @RequestParam(value = "object") String object,
                        HttpServletResponse response) throws Exception {

        //获取视频文件流
        InputStream inputStream = null;
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

        try {
            //获取流
            inputStream = minioClientUtils.getObject(bucket, object);;
            //流转换
            IOUtils.copy(inputStream, response.getOutputStream());
            //设置返回类型
            response.addHeader("Content-Type", "video/mp4;charset=utf-8");
            response.flushBuffer();
        } catch (Exception bd) {
            log.error("BaseException:", bd);
            response.sendError(500, bd.getMessage());
        }
    }

    @GetMapping(value = "/minio/video3")
    void downloadStream3(@RequestParam(value = "bucket") String bucket,
                         @RequestParam(value = "object") String object,
                         HttpServletResponse response) throws Exception {

        //获取视频文件流
        InputStream inputStream = null;
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

        try {
            //获取流
            inputStream = minioClientUtils.getObject(bucket, object);;
            //inputStream = storageService.download(REPOSITORY,"/", name);
            //StatObjectResponse objectStat = storageService.getObjectStat(REPOSITORY,"/", name);

            //response.setContentType(objectStat.contentType());
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("naaaame", "UTF-8"));

            IOUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
        } catch (Exception bd) {
            log.error("BaseException:", bd);
            response.sendError(500, bd.getMessage());
        }
    }

}
