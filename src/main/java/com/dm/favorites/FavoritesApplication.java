package com.dm.favorites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FavoritesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FavoritesApplication.class, args);
    }

}
