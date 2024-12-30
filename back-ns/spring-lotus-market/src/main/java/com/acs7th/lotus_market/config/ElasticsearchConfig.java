package com.acs7th.lotus_market.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ElasticsearchConfig {

    @Value("${spring.data.elasticsearch.url}")
    private String esUrl;

    @Bean
    public ElasticsearchClient elasticsearchClient() throws InterruptedException {
        RestClient restClient = RestClient.builder(
            new HttpHost(esUrl, 9200)
        ).build();

        ElasticsearchTransport transport = new RestClientTransport(
            restClient,
            new co.elastic.clients.json.jackson.JacksonJsonpMapper()
        );

        ElasticsearchClient client = new ElasticsearchClient(transport);

        int retryCount = 0;
        while (retryCount < 10) {
            try {
                if (client.ping().value()) { // 연결 확인
                    System.out.println("Elasticsearch is ready!");
                    break;
                }
            } catch (IOException e) {
                System.out.println("Waiting for Elasticsearch to be ready...");
                Thread.sleep(5000); // 5초 대기
            }
            retryCount++;
        }

        if (retryCount == 10) {
            throw new RuntimeException("Elasticsearch connection failed after 10 attempts.");
        }

        return client;
    }
}
