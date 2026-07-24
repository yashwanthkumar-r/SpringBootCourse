package com.codingshuttle.week_09_learn_spring_ai.service;

import com.codingshuttle.week_09_learn_spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);
    }

    public void ingestDataToVectorStore() {
        //to store any data to vectorStore first we have to convert data into document
/*        Document document = new Document(text);
        vectorStore.add(List.of(document));*/

        List<Document> movies = List.of(
                new Document(
                        "A skilled thief enters people's dreams to steal secrets but is offered a chance to erase his past by planting an idea.",
                        Map.of(
                                "title", "Inception",
                                "genre", "Science Fiction",
                                "year", 2010
                        )
                ),
                new Document(
                        "A young lion prince must overcome guilt and reclaim his kingdom from his treacherous uncle.",
                        Map.of(
                                "title", "The Lion King",
                                "genre", "Animation",
                                "year", 1994
                        )
                ),
                new Document(
                        "A poor young man and a wealthy woman fall in love aboard a doomed luxury ship.",
                        Map.of(
                                "title", "Titanic",
                                "genre", "Romance",
                                "year", 1997
                        )
                )
        );
        vectorStore.add(movies);
    }


    public List<Document> similaritySearch(String text) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(text)
                .topK(2)
                .similarityThreshold(0.3)
                .build());
    }

    public String getJoke(String topic) {

        String systemPrompt = """
                you are a sarcastic joker, you make poetic jokes in 4 lines.
                You don't make jokes about politics.
                Give a joke on the topic: {topic}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        String renderedText = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(renderedText)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .entity(Joke.class);

        return response.text();
    }
}
