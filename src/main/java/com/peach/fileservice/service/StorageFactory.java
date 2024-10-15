package com.peach.fileservice.service;

import com.peach.fileservice.common.StroageEnum;
import com.peach.fileservice.service.impl.AliOssStorageImpl;
import com.peach.fileservice.service.impl.S3StroageImpl;

import java.util.Map;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 15:24
 */
public class StorageFactory {

    public static AbstractFileStorageService createStorageService(Map<String, String> properties) {
        String stroageType = properties.get("type");
        stroageType = stroageType == null ? StroageEnum.ALI_OSS.getCode() : stroageType;
        AbstractFileStorageService storageService = null;
        switch(stroageType){
            case "aliOss":
                storageService = new AliOssStorageImpl();
                break;
            case "s3":
                storageService = new S3StroageImpl();
                break;
            default:
                storageService = new AliOssStorageImpl();
                break;
        }
        return storageService;
    }

}
