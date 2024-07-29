package org.example.concurrencydemo.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;

public class InternalFeignConfiguration {
    @Bean
    public Client client() {
        return new ApacheHttpClient(HttpClients.createDefault());
    }
}
