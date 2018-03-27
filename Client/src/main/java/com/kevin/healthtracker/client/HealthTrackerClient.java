package com.kevin.healthtracker.client;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevin.healthtracker.datamodels.User;

public class HealthTrackerClient {
    private String url;
    private RestTemplate restTemplate;

    public HealthTrackerClient(RestTemplate restTemplate, String host, int port) {
        this.url = String.format("http://%s:%d/healthtracker", host, port);
        this.restTemplate = restTemplate;
    }

    public User registerUser(User user) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.POST, "/users/register", user);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<User>() {
        }).getBody();
    }

    public Boolean loginUser(User user) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.POST, "/users/login", user);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Boolean>() {
        }).getBody();
    }

    public User changePassword(User user) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.PUT, "/users/changepassword", user);
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

    public ResponseEntity<Void> deleteUser(int id) throws JsonProcessingException {
        RequestEntity requestEntity = createRequestEntity(HttpMethod.DELETE, "/users/" + id, null);
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Void>() {
        });
    }

    private RequestEntity<User> createRequestEntity(HttpMethod method, String endpoint, User user) throws JsonProcessingException {
        return RequestEntity.method(method, URI.create(url + endpoint))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(user);
    }
}
