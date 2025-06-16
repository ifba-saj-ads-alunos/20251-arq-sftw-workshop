package br.ifba.ads.workshop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.ifba.ads.workshop.core")
public class WorkshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopApiApplication.class, args);
    }
} 