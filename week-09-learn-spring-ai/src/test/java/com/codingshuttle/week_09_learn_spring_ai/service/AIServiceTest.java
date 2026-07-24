package com.codingshuttle.week_09_learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTest {

    @Autowired
    private AIService aiService;

//    @Test
//    public void testAskAI(){
//        var res = aiService.askAI("What is my name");
//        System.out.println(res);
//    }

//    @Test
//    public void testStoreData(){
//       // aiService.ingestDataToVectorStore();
//        aiService.springAIDocs();
//    }

/*
    @Test
    public void testGetJoke(){
        var joke = aiService.getJoke("Cats");
        System.out.println(joke);
    }

    @Test
    public void testGetEmbedding(){
        var embed = aiService.getEmbedding("This is a big number");
        System.out.println(embed.length);
        for(float f: embed){
            System.out.print(f+" ");
        }
    }

    @Test
    public void testSimilaritySearch(){
        var res = aiService.similaritySearch("man and a wealthy women fall in love movie");
        for(var r: res){
            System.out.println(r);
        }
    }
*/


}
