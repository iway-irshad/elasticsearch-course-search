package com.undoschool.coursesearch.config;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // Create a custom Jackson ObjectMapper with JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Use this object mapper with JacksonJsonpMapper
        JacksonJsonpMapper mapper = new JacksonJsonpMapper(objectMapper);

        // Set up the low-level REST client
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();

        // Combine everything
        RestClientTransport transport = new RestClientTransport(restClient, mapper);

        return new ElasticsearchClient(transport);
    }
}
