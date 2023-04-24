package com.meng.minio.util;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MinIO 客户端工具类
 * https://blog.csdn.net/weixin_73510682/article/details/127899606
 * Object: 存储到MinIO的基本对象，如文件、字节流...
 * Bucket: 用来存储Object的逻辑空间。每个Bucket之间的数据是相互隔离的。
 * Drive: 部署MinIO时设置的磁盘，MinIO中所有的对象数据都会存储到Drive。
 * Set: 一组Drive的集合，MinIO会自动根据Drive数量，将若干个Drive划分为多个Set（For example: {1...64} is divided into 4 sets each of size 16
 */
@Component
@Slf4j
public class MinioClientUtil {

    @Resource(name = "minioClient")
    private MinioClient minioClient;

    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;


    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        // Check whether 'bucketname' exists or not.
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    @SneakyThrows
    public boolean makeBucket(String bucketName) {
        boolean found = bucketExists(bucketName);
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 列出所有存储桶名称
     *
     * @return List<String>
     */
    @SneakyThrows
    public List<String> listBucketNames() {
        List<Bucket> bucketList = listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    /**
     * 列出所有存储桶
     *
     * @return List<Bucket>
     */
    @SneakyThrows
    public List<Bucket> listBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 删除存储桶,空桶
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    @SneakyThrows
    public boolean removeBucket(String bucketName) {
        boolean found = bucketExists(bucketName);
        if (!found) {
            return false;
        }
        Iterable<Result<Item>> myObjects = listObjects(bucketName);
        for (Result<Item> result : myObjects) {
            Item item = result.get();
            // 有对象文件，则删除失败
            if (item.size() > 0) {
                return false;
            }
        }
        // 删除存储桶，注意，只有存储桶为空时才能删除成功。
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        found = bucketExists(bucketName);
        if (!found) {
            return true;
        }
        return false;
    }

    /**
     * 列出存储桶中的所有当前目录对象名称
     *
     * @param bucketName 存储桶名称
     * @return List<String>
     */
    @SneakyThrows
    public List<String> listObjectNames(String bucketName) {
        List<String> listObjectNames = new ArrayList<>();
        boolean found = bucketExists(bucketName);
        if (found) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        }
        return listObjectNames;
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return Iterable<Result<Item>>
     */
    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        boolean found = bucketExists(bucketName);
        if (found) {
            return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        }
        return Collections.emptyList();
    }

    /**
     * 通过本地文件上传到对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return boolean
     */
    @SneakyThrows
    public boolean uploadObject(String bucketName, String objectName, String fileName)  {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName).object(objectName).filename(fileName).build());
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过本地视频上传到对象
     * minioClient.uploadObject( UploadObjectArgs.builder().bucket("my-bucketname").object("my-objectname").filename("my-video.avi").contentType("video/mp4").build());
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return boolean
     */
    @SneakyThrows
    public boolean uploadVideoObject(String bucketName, String objectName, String fileName, String type) {
        boolean found = bucketExists(bucketName);
        if (found) {
            // Upload a video file.
            minioClient.uploadObject(
                UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .filename(fileName)
                    .contentType(type)
                    .build());
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件上传-文件流
     *
     * @param bucketName 存储捅名称
     * @param multipartFile 文件
     * @param filename   文件名
     */
    @SneakyThrows
    public boolean putObject(String bucketName, MultipartFile multipartFile, String filename) {
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(filename)
                        .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                        .contentType(multipartFile.getContentType())
                        .build());
        StatObjectResponse statObject = statObject(bucketName, filename);
        if (statObject != null && statObject.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过InputStream上传对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param inputStream     要上传的流
     * @param contentType 上传的文件类型 例如 video/mp4  image/jpg
     * @return boolean
     */
    @SneakyThrows
    public boolean putObject(String bucketName, String objectName, InputStream inputStream,String contentType) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                            //不清楚文件的大小时，可以传-1，10485760。如果知道大小也可以传入size，partsize。
                            inputStream,  -1, 10485760)
                    .contentType(contentType)
                    .build());
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return InputStream
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                InputStream stream = minioClient.getObject( GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
                return stream;
            }
        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象（断点下载）
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return InputStream
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName, long offset, Long length)  {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                InputStream stream = minioClient.getObject(  GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .offset(offset) //.offset(1024L)
                        .length(length) //.length(4096L)
                        .build());
                return stream;
            }
        }
        return null;
    }

    /**
     * 下载并将文件保存到本地
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return boolean
     */
    @SneakyThrows
    public boolean downloadObject(String bucketName, String objectName, String fileName)  {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                minioClient.downloadObject(DownloadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(fileName)
                        .build());
                return true;
            }
        }
        return false;
    }

    /**
     * 下载并将文件保存到response输出流
     *
     * @param bucketName 存储桶名称
     * @param bucketName   存储桶里的对象名称
     * @param objectName   File name
     * @return
     */
    @SneakyThrows
    public boolean getObject(String bucketName, String objectName, HttpServletResponse response) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(objectName, "utf-8"));
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
                IOUtils.copy(object, response.getOutputStream());
                object.close();
                return true;
            }
        }
        return false;
    }

    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    @SneakyThrows
    public boolean removeObject(String bucketName, String objectName)  {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        }
        return false;
    }

    /**
     * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
     *
     * @param bucketName  存储桶名称
     * @param objectNames 含有要删除的多个object名称的迭代器对象
     * @return
     * eg:
     * List<DeleteObject> objects = new LinkedList<>();
     * objects.add(new DeleteObject("my-objectname1"));
     * objects.add(new DeleteObject("my-objectname2"));
     * objects.add(new DeleteObject("my-objectname3"));
     */
    @SneakyThrows
    public List<String> removeObjects(String bucketName, List<DeleteObject> objectNames)  {
        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(objectNames).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                deleteErrorNames.add(error.objectName());
            }
        }
        return deleteErrorNames;
    }

    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */
    @SneakyThrows
    public String presignedGetObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                throw new Exception("expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
            }
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).build());
        }
        return url;
    }

    /**
     * 生成一个给HTTP PUT请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return String
     */
    @SneakyThrows
    public String presignedPutObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                throw new Exception("expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
            }
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(expires)//动态参数
                    //                       .expiry(24 * 60 * 60)//用秒来计算一天时间有效期
//                        .expiry(1, TimeUnit.DAYS)//按天传参
//                        .expiry(1, TimeUnit.HOURS)//按小时传参数
                    .build());
        }
        return url;
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public StatObjectResponse statObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObject =
                     minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return statObject;
        }
        return null;
    }

    /**
     * 文件访问路径
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return String
     */
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName)
                    .object(objectName).build());
        }
        return url;
    }


    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) {
        try {
            InputStream file = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            String filename = new String(fileName.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
            if (originalName != null && !"".equals(originalName)) {
                fileName = originalName;
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (ErrorResponseException e) {
            log.error("ErrorResponseException",e);
        } catch (Exception e) {
            log.error("Exception",e);
        }
    }
}
