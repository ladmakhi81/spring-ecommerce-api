package com.codyproo.ecommerce.services;

public interface HttpClient {
    <T> T post(String url, Object dto, Class<T> responseType) throws Exception;
}
