package com.joe.movies.movie;

import com.joe.movies.exception.InvalidRatingException;
import com.joe.movies.genre.Genre;
import com.joe.movies.rating.Rating;
import com.joe.movies.security.Identifier;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private Identifier identifier;

    @GetMapping("/")
    public List<Movie> getTrendingMovies() throws IOException {
        return this.movieService.getMovies();
    }

    @PostMapping("/rating")
    public void addRating(@RequestBody Rating rate,@RequestHeader(name = "auth-x") String token) throws InvalidRatingException {
        String username = identifier.getUsername(token);
        this.movieService.addRating(rate,username);
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() throws IOException {
        return this.movieService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public List<Movie> getAllMoviesWithGenre(@PathVariable int id){
        return this.movieService.moviesOfGenre(id);
    }
}
