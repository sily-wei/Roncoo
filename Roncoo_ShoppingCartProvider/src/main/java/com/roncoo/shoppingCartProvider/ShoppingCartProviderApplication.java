package com.roncoo.shoppingCartProvider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author weining
 * @date 2020/1/16 21:02
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.**.dao")
public class ShoppingCartProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartProviderApplication.class,args);
    }
}
