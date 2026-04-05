package com.banka1.order.config;

import com.banka1.order.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Profile("!local")
public class RestClientConfig {

    private final JWTService jwtService;

    class JwtAuthInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            String token = jwtService.generateJwtToken();
            request.getHeaders().set("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        }
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder()
                .requestInterceptor(new JwtAuthInterceptor());
    }

    @Bean
    public RestClient accountRestClient(RestClient.Builder builder, @Value("${services.account.url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    public RestClient employeeRestClient(RestClient.Builder builder, @Value("${services.employee.url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    public RestClient clientRestClient(RestClient.Builder builder, @Value("${services.client.url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    public RestClient exchangeRestClient(RestClient.Builder builder, @Value("${services.exchange.url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    public RestClient stockRestClient(RestClient.Builder builder, @Value("${services.stock.url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }
}
