package com.peach.fileservice.controller;

import com.peach.common.manager.RedisCaffeineCache;
import com.peach.common.manager.RedisCaffeineCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2024/10/9 14:40
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestContrller {

    @Autowired
    RedisCaffeineCacheManager cacheManager;

//    @GetMapping("/{id}")
//    public Map test(@PathVariable("id") String id){
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("id",id);
//        resultMap.put("name","Mr Shu");
//        resultMap.put("age",20);
//        log.error("resultMap:[{}]", JSON.toJSONString(resultMap));
//        return resultMap;
//    }
    @Autowired
    ExampleService exampleService;

    @GetMapping("/{id}")
    public String test1(@PathVariable("id") String id){

        RedisCaffeineCache cache = (RedisCaffeineCache) cacheManager.getCache("myCache");
        Object o = cache.lookup("id");
        Collection<String> cacheNames = cacheManager.getCacheNames();
        log.error("cacheNames:[{}]" ,cacheNames);
        return String.valueOf(o);
    }

    @PostMapping("")
    public String test2(){
        putInCache("myCache","id",500);
        return "success";
    }
    public void putInCache(String cacheName, Object key, Object value) {
        // 获取 RedisCaffeineCache 对象
        RedisCaffeineCache cache = (RedisCaffeineCache) cacheManager.getCache(cacheName);

        if (cache != null) {
            // 添加缓存
            cache.put(key, value);
        }
    }

    @GetMapping("/test")
    public String test3(){
        Integer id = exampleService.getDataById("id");
        return String.valueOf(id);
    }

}
