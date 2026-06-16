package com.example.week_06_Testing.controllers;

import com.example.week_06_Testing.repositories.TestContainersConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@AutoConfigureWebTestClient(timeout = "100000")
@Import(TestContainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {
}
