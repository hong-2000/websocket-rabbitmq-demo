package com.demo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-17 20:46
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ProducerRabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerRabbitmqApplication.class,args);
    }
}
