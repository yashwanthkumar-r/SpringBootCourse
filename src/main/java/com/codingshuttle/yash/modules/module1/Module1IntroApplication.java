package com.codingshuttle.yash.modules.module1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Module1IntroApplication implements CommandLineRunner {

    private CakeBaker cakeBaker;

    public Module1IntroApplication(CakeBaker cakeBaker) {
        this.cakeBaker = cakeBaker;
    }

    public static void main(String[] args) {
        SpringApplication.run(Module1IntroApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        cakeBaker.bakeCake();
    }
}
