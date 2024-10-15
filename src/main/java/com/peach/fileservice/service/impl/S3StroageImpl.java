package com.peach.fileservice.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.peach.fileservice.config.FileProperties;
import com.peach.fileservice.service.AbstractFileStorageService;
import com.peach.fileservice.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 16:38
 */
@Slf4j
@Component
public class S3StroageImpl extends AbstractFileStorageService {

    @Override
    public boolean copyDir(String sourceDir, String targetDir) {
        return false;
    }

    @Override
    public boolean downDir(String sourceDir, String localDir) {
        return false;
    }

    @Override
    public String upload(InputStream inputStream, String targetPath, String fileName) {
        return upLoadInputStream(inputStream,targetPath,fileName);
    }

    @Override
    public String upload(String content, String targetPath, String fileName) {
        return "";
    }

    @Override
    public List<String> upload(File[] file, String targetPath) {
        return Collections.emptyList();
    }

    @Override
    public String upload(File file, String targetPath, String fileName) {
        return "";
    }

    @Override
    public boolean download(String targetPath, String localPath, String fileName) {
        return false;
    }

    @Override
    public InputStream getInputStream(String targetPath, String fileName) {
        return null;
    }

    @Override
    public InputStream getInputStreamByKey(String key) {
        return null;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public boolean copyFile(String currentPath, String targetPath) {
        return false;
    }

    @Override
    public String getUrlByKey(String key) {
        return "";
    }

    @Override
    public String getPathByKey(String key) {
        return "";
    }

    @Override
    public void setPublicReadAcl(String path) {

    }

    public String upLoadInputStream(InputStream inputStream, String targetPath, String fileName) {
        FileProperties fileProperties = SpringContextHolder.getBean("fileProperties");
        String url = null;
        String localPath = targetPath.endsWith(PATH_SEPARATOR) ? targetPath : targetPath + PATH_SEPARATOR;
        String key = localPath + fileName;
        AmazonS3 s3Client = fileProperties.getS3Client();
        String keyPath = fileProperties.getOssKey(key);
        String bucketName = fileProperties.getBucketName();
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream(); InputStream in = inputStream) {
            byte[] data = new byte[2048];
            int reader;
            while ((reader = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, reader);
            }
            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(byteArray.length);
            s3Client.putObject(bucketName, keyPath, new ByteArrayInputStream(byteArray), objectMetadata);
            try {
                s3Client.setObjectAcl(bucketName, keyPath, CannedAccessControlList.PublicRead);
                log.info("set PublicRead success:" + key);
            } catch (SdkClientException e) {
                log.error("设置文件权限失败", e);
            }
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyPath);
            String ossUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
            if (ossUrl.contains("https://")) {
                url = ossUrl.replaceAll("https://[^/]+", "/");
            } else {
                url = ossUrl.replaceAll("http://[^/]+", "/");
            }
            if (URL_TAKE_SIGN_NO == fileProperties.getUrlTakeSign()) {
                url = url.split("\\?")[0];
            }
        } catch (Exception e) {
            log.error("upLoadInputStream error"+e,e.getMessage());
        } finally {
            try {
                s3Client.shutdown();
            } catch (Exception e) {
                log.error("s3Client.shutdown() error:"+e,e.getMessage());
            }
        }
        return url;
    }

}
