package com.codingshuttle.week_09_learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTest {

    @Autowired
    private RAGService ragService;

    @Test
    public void testIngest(){
        ragService.ingestPdfToVectorStore();
    }

    @Test
    public void testAskAI(){
        var res = ragService.askAI("is there any dress code for employees to follow");
        System.out.println(res);
    }
}
