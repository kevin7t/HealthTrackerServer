package com.kevin.healthtracker.client;

import java.net.URI;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.healthtracker.datamodels.User;

public class HealthTrackerClient {
    private String url;
    private RestTemplate restTemplate;

    public HealthTrackerClient(RestTemplate restTemplate, String host, int port) {
        this.url = String.format("http://%s:%d/healthtracker", host, port);
        this.restTemplate = restTemplate;
    }

    public Integer registerUser(User user) {
        RequestEntity requestEntity = null;
        try {
            requestEntity = createRequestEntity(HttpMethod.POST, "/users/register", user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Integer>() {
        }).getBody();
    }

    public Boolean loginUser(User user)  {
        RequestEntity requestEntity = null;
        try {
            requestEntity = createRequestEntity(HttpMethod.POST, "/users/login", user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Boolean>() {
        }).getBody();
    }

    private RequestEntity<User> createRequestEntity(HttpMethod method, String endpoint, User user) throws JsonProcessingException {
        return RequestEntity.method(method, URI.create(url + endpoint))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(user);
    }
}
