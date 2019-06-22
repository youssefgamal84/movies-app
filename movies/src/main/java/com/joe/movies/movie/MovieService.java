package com.joe.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    public static final String API_URL = "";

    @Autowired
    private MovieRepo movieRepo;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public Movie[] getMovies(){

    }
}
