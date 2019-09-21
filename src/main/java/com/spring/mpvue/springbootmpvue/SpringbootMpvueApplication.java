package com.spring.mpvue.springbootmpvue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.spring.mpvue.springbootmpvue.mybatis.mapper")
public class SpringbootMpvueApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMpvueApplication.class, args);
    }
}
