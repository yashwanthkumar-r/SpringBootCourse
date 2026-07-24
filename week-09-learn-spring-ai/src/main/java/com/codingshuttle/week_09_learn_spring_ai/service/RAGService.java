package com.codingshuttle.week_09_learn_spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    @Value("classpath:testDocForRAG.pdf")
    Resource pdfFile;


    public String askAI(String prompt){
        String template = """
        You are an AI assistant helping developers.

        Rules:
        - Use the provided context to answer the user's question.
        - Summarize the answer in natural language that is easy to understand.
        - If multiple relevant contexts are found, combine them into one clear answer.
        - If the answer is not found in the context and only use the context to answerx, say: "I don't know."

        Context:
        {context}

        Answer in a friendly way.
        """;

        //search in vectorDb for the similar context of "prompt"
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .topK(4)
                .similarityThreshold(0.4)
                .filterExpression("file_name=='testDocForRAG.pdf'")
                .build());

        //covert Document data into string(split by new line"\n")
        String context = documents.stream()
                .map(Document->Document.getText())
                .collect(Collectors.joining("\n\n"));


        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of("context",context));

        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .content();
    }

    public void ingestPdfToVectorStore(){
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfFile);
        List<Document> pages = reader.get();

        //config the tokenSplitter to break the pages into chucks
        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        //apply tokenSplitter to the pages
        List<Document> chucks = tokenTextSplitter.apply(pages);
        vectorStore.add(chucks);
    }

    public void springAIDocs(){
        List<Document> documents = List.of(
                new Document("Spring Boot simplifies Java application development using auto-configuration and embedded servers.",
                        Map.of("topic", "Spring Boot", "category", "Introduction")),

                new Document("@SpringBootApplication combines configuration, auto-configuration, and component scanning.",
                        Map.of("topic", "Spring Boot", "category", "Annotations")),

                new Document("@RestController and mapping annotations are used to create REST APIs in Spring Boot.",
                        Map.of("topic", "Spring Boot", "category", "REST API")),

                new Document("JpaRepository provides built-in database methods such as save, findById, and deleteById.",
                        Map.of("topic", "Spring Boot", "category", "Database"))
        );

        vectorStore.add(documents);
    }

}
