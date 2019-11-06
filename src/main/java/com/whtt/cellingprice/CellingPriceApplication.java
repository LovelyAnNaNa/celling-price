package com.whtt.cellingprice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.whtt.cellingprice.mapper")
public class CellingPriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CellingPriceApplication.class, args);
    }

}
