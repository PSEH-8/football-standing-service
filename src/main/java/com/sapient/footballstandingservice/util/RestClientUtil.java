package com.sapient.footballstandingservice.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class RestClientUtil {
    RestTemplate restTemplate;

    @PostConstruct
    void createRestTemplate() {
        restTemplate = new RestTemplate(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;

    }

    public <T> List<T> fetchData(String resourceUrl, Class<T> clazz, HttpEntity httpEntity) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<T>> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<T>>() {
        });
        List<T> foo = response.getBody();
        return foo;
    }

}
