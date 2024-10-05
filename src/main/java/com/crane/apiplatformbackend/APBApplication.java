package com.crane.apiplatformbackend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.crane.apiplatformbackend.mapper")
@Slf4j
public class APBApplication {

    public static void main(String[] args) {
        SpringApplication.run(APBApplication.class, args);
        log.info("knife4j的接口文档地址为：http://localhost:8080/doc.html");
    }

}
