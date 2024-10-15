package com.peach.fileservice.controller;

import com.peach.fileservice.config.FileProperties;
import com.peach.fileservice.service.AbstractFileStorageService;
import com.peach.fileservice.service.StorageFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 14:40
 */
@Slf4j
@RestController
@RequestMapping("/test/oss")
public class OssContrller {

    @Autowired
    private FileProperties fileProperties;

    @PostMapping()
    public Map upload(@RequestPart("file") MultipartFile file){
        Map<String, String> props = new HashMap<>();
        props.put("type", fileProperties.getType());
        AbstractFileStorageService storageService = StorageFactory.createStorageService(props);
        String filePath = null;
        try {
            filePath = storageService.upload(file.getInputStream(), "/data/file/test", file.getOriginalFilename());
        }catch (Exception ex){
            throw new RuntimeException("上传文件失败");
        }
        props.clear();
        props.put("msg","sucess");
        props.put("option","uploadFile");
        props.put("path",filePath);
        return props;
    }
}
