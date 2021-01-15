package com.dwy.logistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: DongWenYu
 * @Date: 2020/12/29 9:28
 */
@SpringBootApplication
@MapperScan("com.dwy.logistics.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
