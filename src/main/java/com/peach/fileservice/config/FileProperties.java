package com.peach.fileservice.config;

import com.aliyun.oss.OSSClient;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.peach.common.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 15:13
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "file.storage")
public class FileProperties {

    /**
     * 存储类型
     */
    private String type;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 登录节点
     */
    private String endpoint;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 地域
     */
    private String region;

    /**
     * 实例化方式
     */
    private String instanceType;

    /**
     * 是否替换签名
     */
    private Integer urlTakeSign;

    /**
     * 是否启用路径样式访问
     */
    private Boolean pathStyleAccessEnabled;


    public OSSClient getOssClient() {
        checkParams();
        return new OSSClient(endpoint, accessKey, secretKey);
    }

    public AmazonS3 getS3Client(){
        checkParams();
        AWSStaticCredentialsProvider awsCredentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey));
        return AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentials)
                .withPathStyleAccessEnabled(pathStyleAccessEnabled)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();
    }

    /**
     * 校验三个必填参数
     */
    private void checkParams() {
        if (StringUtil.isEmpty(endpoint)){
            log.error("endpoint is empty");
            throw new RuntimeException("endpoint is empty");
        }
        if (StringUtil.isEmpty(accessKey)){
            log.error("accessKey is empty");
            throw new RuntimeException("accessKey is empty");
        }
        if (StringUtil.isEmpty(secretKey)){
            log.error("secretKey is empty");
            throw new RuntimeException("secretKey is empty");
        }
    }

    public String getOssKey(String dirName) {
        boolean isFirst = true;
        StringBuilder result = new StringBuilder();
        List<String> dirPathList = new LinkedList<>();
        String[] str = dirName.split("\\?");
        String path = str[0];
        String[] dirSins = path.split("/");
        for (String dirsin : dirSins) {
            if (StringUtil.isBlank(dirsin)) {
                continue;
            }
            String[] childDirs = dirsin.split("\\\\");
            for (String childDir : childDirs) {
                if (StringUtil.isNotBlank(childDir)) {
                    dirPathList.add(childDir);
                }
            }
        }
        for (String dirPath : dirPathList) {
            if (isFirst) {
                result = new StringBuilder(dirPath);
                isFirst = false;
            } else {
                result.append("/").append(dirPath);
            }
        }
        return result.toString();
    }


}

