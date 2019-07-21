package com.postgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用程序启动类。
 *
 * @author elon
 * @version 1.0
 */
@SpringBootApplication
public class StartupPostgresApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartupPostgresApplication.class, args);
        System.out.println("Start up success!");
    }
}
