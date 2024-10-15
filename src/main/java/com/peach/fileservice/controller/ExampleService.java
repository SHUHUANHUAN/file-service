package com.peach.fileservice.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    @Cacheable(value = "myCache", key = "#id")
    public Integer getDataById(String id) {
        // 假设这里有一个耗时的数据库或远程服务查询
        return 0;
    }
}