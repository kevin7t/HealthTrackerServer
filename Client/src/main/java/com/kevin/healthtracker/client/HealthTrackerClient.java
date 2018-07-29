package com.kevin.healthtracker.client;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import org.springframework.web.client.RestTemplate;

public class HealthTrackerClient {
    private String url;
    private RestTemplate restTemplate;

    public HealthTrackerClient(RestTemplate restTemplate, String host, int port) {
        this.url = String.format("http://%s:%d/healthtracker", host, port);
        this.restTemplate = restTemplate;
    }

    public User registerUser(User user) {
        return restTemplate.postForObject(url + "/users/register", user, User.class);
    }

    public Boolean loginUser(User user) {
        return restTemplate.postForObject(url + "/users/login", user, Boolean.class);
    }

    public User changePassword(User user) {
        return restTemplate.postForObject(url + "/users/changepassword", user, User.class);
    }

    public User getUser(int id) {
        return restTemplate.getForObject(url + "/users/" + id, User.class);
    }

    public User[] getAllUsers() {
        return restTemplate.getForObject(url + "/users", User[].class);
    }

    public void deleteUser(int id) {
        restTemplate.delete(url + "/users/" + id);
    }

    //Todo null object as request?
    public Friend addFriend(int user1Id, int user2Id) {
        return restTemplate.postForObject(String.format("%s/users/addfriend/%s/%s", url, user1Id, user2Id), null, Friend.class);
    }

    public Friend acceptFriend(int user1Id, int user2Id) {
        return restTemplate.postForObject(String.format("%s/users/acceptfriend/%s/%s", url, user1Id, user2Id), null, Friend.class);
    }

    public Friend declineFriend(int user1Id, int user2Id) {
        return restTemplate.postForObject(String.format("%s/users/declinefriend/%s/%s", url, user1Id, user2Id), null, Friend.class);
    }

    public void deleteFriend(int user1Id, int user2Id) {
        restTemplate.delete(String.format("%s/users/deletefriend/%s/%s", url, user1Id, user2Id), null, Friend.class);
    }

    public Friend[] getOutboundRequests(int user1Id) {
        return restTemplate.getForObject(url + "/users/getoutboundrequests/" + user1Id, Friend[].class);
    }

    public Friend[] getInboundRequests(int user1Id) {
        return restTemplate.getForObject(url + "/users/getinboundrequests/" + user1Id, Friend[].class);
    }

    public Friend[] getAllFriends(int user1Id) {
        return restTemplate.getForObject(url + "/users/getallrelations/" + user1Id, Friend[].class);
    }

    public Friend getFriend(int user1Id, int user2Id) {
        return restTemplate.getForObject(String.format("%s/users/getfriend/%s/%s", url, user1Id, user2Id), Friend.class);
    }

}
