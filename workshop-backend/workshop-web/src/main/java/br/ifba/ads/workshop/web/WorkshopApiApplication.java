package br.ifba.ads.workshop.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "br.ifba.ads.workshop.web",
        "br.ifba.ads.workshop.infra",
})
public class WorkshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopApiApplication.class, args);
    }
} 