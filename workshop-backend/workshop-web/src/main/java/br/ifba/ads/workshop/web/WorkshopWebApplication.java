package br.ifba.ads.workshop.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "br.ifba.ads.workshop.infra",
        "br.ifba.ads.workshop.web"
})
@EnableJpaRepositories(basePackages = "br.ifba.ads.workshop.infra.persistence")
@EntityScan(basePackages = "br.ifba.ads.workshop.infra.persistence")
public class WorkshopWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopWebApplication.class, args);
    }
} 
