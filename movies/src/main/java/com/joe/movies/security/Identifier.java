package com.joe.movies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class Identifier {

    @Autowired
    RestTemplate restTemplate;

    public String getUsername(String token) {
        ResponseEntity response = restTemplate.postForEntity("http://auth-service/identify", token, String.class);
        return response.getBody().toString();
    }
}
