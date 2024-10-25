package com.crane.apiplatformbackend;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Crane Resigned
 */
@SpringBootApplication
@MapperScan("com.crane.apiplatformbackend.mapper")
@Slf4j
@EnableDubbo
public class APBApplication {

    public static void main(String[] args) {
        SpringApplication.run(APBApplication.class, args);
        log.info("knife4j的接口文档地址为：http://localhost:8080/api/doc.html");
        log.info("nacos：http://localhost:8848/nacos");
    }

}
