package com.kevin.healthtracker.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevin.healthtracker.datamodels.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

public class HealthTrackerClient {
    private String url;
    private RestTemplate restTemplate;

    public HealthTrackerClient(RestTemplate restTemplate, String host, int port) {
        this.url = String.format("http://%s:%d/healthtracker", host, port);
        this.restTemplate = restTemplate;
    }

    public Integer registerUser(User user) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.POST, "/users/register", user);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Integer>() {
        }).getBody();
    }

    public Boolean loginUser(User user) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.POST, "/users/login", user);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Boolean>() {
        }).getBody();
    }

    public User changePassword(User user) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.PUT, "/users/changePassword", user);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<User>() {
        }).getBody();
    }

    public User getUser(int id) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.GET, "/users/" + id, null);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<User>() {
        }).getBody();
    }

    public List<User> getAllUsers() throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.GET, "/users", null);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    public ResponseEntity deleteUser(int id) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.DELETE, "/users/" + id, null);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<ResponseEntity>() {
        });
    }

    private RequestEntity<User> createRequestEntity(HttpMethod method, String endpoint, User user) throws JsonProcessingException {
        return RequestEntity.method(method, URI.create(url + endpoint))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(user);
    }
}
