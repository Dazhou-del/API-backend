package com.dazhou.dzapiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DzapiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DzapiInterfaceApplication.class, args);
    }

}
