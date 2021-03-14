package com.yaohl0911;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.yaohl0911.mapper")  // 定义Mybatis的Mapper的扫描路径
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
