package com.roncoo.eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author weining
 * @date 2020/1/16 19:48
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerAppliation {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerAppliation.class,args);
    }
}
