package com.jm.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application config. Beans definition.
 * @author jmugico
 */
@SpringBootApplication(scanBasePackages={"com.jm.urlshortener"})
public class AppConfig {
    
    public static void main(String[] args) {
	SpringApplication.run(AppConfig.class, args);
    }
}