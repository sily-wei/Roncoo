package com.roncoo.courseProvider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author weining
 * @date 2020/1/16 20:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.**.dao")
public class CourseProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseProviderApplication.class,args);
    }
}
