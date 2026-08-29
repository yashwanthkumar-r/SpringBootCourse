package com.codingshuttle.ecommerce.api_gateway.filters;

import com.codingshuttle.ecommerce.api_gateway.service.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }


    @Override
    public GatewayFilter apply(Config config) {


        return (exchange, chain) ->{

            if(!config.isEnabled){
                log.info("config is disabled");
                return chain.filter(exchange);
            }

            String authenticationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            log.info("Authorization Header: {}", authenticationHeader);

            if(authenticationHeader==null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authenticationHeader.split("Bearer ")[1];
            log.info("Token: {}", token);

            Long userId = jwtService.getUserIdFromToken(token);
            log.info("User ID: {}", userId);

            //mutating the request
            ServerHttpRequest request =exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", userId.toString())
                    .build();

            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        };
    }

    @Data
    public static class Config{
        private boolean isEnabled;
    }
}
