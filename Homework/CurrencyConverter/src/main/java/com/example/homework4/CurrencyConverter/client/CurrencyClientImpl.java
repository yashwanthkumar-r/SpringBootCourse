package com.example.homework4.CurrencyConverter.client;

import com.example.homework4.CurrencyConverter.advice.ApiResponse;
import com.example.homework4.CurrencyConverter.dto.CurrencyDto;
import com.example.homework4.CurrencyConverter.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrencyClientImpl implements CurrencyClient {

    private String API_KEY = "fca_live_Cn3Jf6wWnyjUtw9YL7fHyMoyw2zkwsE4YBcsmU6R";

    private RestClient restClient;

    Logger log = LoggerFactory.getLogger(CurrencyClientImpl.class);

    public CurrencyClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ApiResponse<CurrencyDto> getMoneyExchanged(String reqCur) {
        try {
            log.info("Entered into getMoneyExchanged method");
            ApiResponse<CurrencyDto> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest")
                            .queryParam("apikey", API_KEY)
                            .queryParam("currencies", reqCur)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("couldn't get data from currency client", new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("couldn't get data from currency client");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<CurrencyDto>>() {
                    });

            log.info("Successfully got response form client: {}", response.toString());

            return response;
        } catch (Exception e) {
            log.error("Error occurred for the Currency: {}", reqCur);
            throw new RuntimeException(e);
        }
    }
}
