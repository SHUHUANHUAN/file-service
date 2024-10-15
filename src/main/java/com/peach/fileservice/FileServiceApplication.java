package com.peach.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@EnableCaching
@ComponentScan("com.peach.*")
public class FileServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FileServiceApplication.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .web(WebApplicationType.SERVLET)
                .run(args);
        log.info("FileServiceApplication has been started ...");
    }

}
