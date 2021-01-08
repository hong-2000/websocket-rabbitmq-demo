package com.demo.clientparam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description 启动类
 * @create 2020/11/17 14:24
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WebSocketClientParamApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketClientParamApplication.class, args);
    }
}
