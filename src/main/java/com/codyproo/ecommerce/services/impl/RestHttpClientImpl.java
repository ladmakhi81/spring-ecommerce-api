package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.services.HttpClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestHttpClientImpl implements HttpClient {
    private final RestTemplate restTemplate;

    public RestHttpClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T post(String url, Object dto, Class<T> responseType) throws Exception {
        return restTemplate.postForObject(url, dto, responseType);
    }
}
