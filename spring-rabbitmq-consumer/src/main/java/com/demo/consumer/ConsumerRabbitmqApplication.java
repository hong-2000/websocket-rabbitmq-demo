package com.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-17 20:46
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsumerRabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerRabbitmqApplication.class,args);
    }
}
