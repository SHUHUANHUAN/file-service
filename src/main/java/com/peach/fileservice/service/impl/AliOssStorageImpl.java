package com.peach.fileservice.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.peach.fileservice.config.FileProperties;
import com.peach.fileservice.service.AbstractFileStorageService;
import com.peach.fileservice.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 15:00
 */
@Slf4j
@Component
public class AliOssStorageImpl extends AbstractFileStorageService {


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
        return upLoadInputStream(inputStream, targetPath, fileName);
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

    private String upLoadInputStream(InputStream inputStream, String targetPath, String fileName) {

        String url = null;
        String localPath = targetPath.endsWith(PATH_SEPARATOR) ? targetPath : targetPath + PATH_SEPARATOR;
        String key = localPath + fileName;
        FileProperties fileProperties = SpringContextHolder.getBean("fileProperties");
        OSSClient ossClient = fileProperties.getOssClient();
        try {
            //上传文件
            PutObjectResult result = ossClient.putObject(fileProperties.getBucketName(), fileProperties.getOssKey(key), inputStream);
            if (null != result) {
                // 设置URL过期时间为2年
                Date expiration = new Date(System.currentTimeMillis() + EXPIRATION);
                String ossUrl = ossClient.generatePresignedUrl(fileProperties.getBucketName(), fileProperties.getOssKey(key), expiration).toString();
                if (ossUrl.contains("https://")) {
                    url = ossUrl.replaceAll("https://[^/]+", "");
                } else {
                    url = ossUrl.replaceAll("http://[^/]+", "");
                }
            }
        } catch (Exception e) {
            log.error("upload OSS error！", e);
        } finally {
            try {
                ossClient.shutdown();
            } catch (Exception e) {
                log.error("upload OSS error！", e);
            }
        }
        return url;
    }
}
