package com.joe.movies.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.movies.exception.InvalidRatingException;
import com.joe.movies.genre.Genre;
import com.joe.movies.genre.GenreRepo;
import com.joe.movies.rating.Rating;
import com.joe.movies.rating.RatingRepo;
import com.joe.movies.user.User;
import com.joe.movies.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class MovieService {

    @Value("${api.url}")
    private String API_URL;

    @Value("${api.key}")
    private String API_KEY;

    @Value("${api.genres-path}")
    private String GENRES_PATH;

    @Value("${api.trending-movies}")
    private String MOVIES_PATH;

    @Value("${api.key-prefix}")
    private String KEY_PREFIX;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MovieGenreRepo movieGenreRepo;

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private GenreRepo genreRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    public List<Movie> getMovies() throws IOException {
        String url = API_URL + MOVIES_PATH + KEY_PREFIX + API_KEY;
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.getForEntity(url, String.class);
        JsonNode root = mapper().readTree(response.getBody());
        JsonNode results = root.path("results");
        Movie[] movies = mapper().treeToValue(results, Movie[].class);

        saveMovies(movies);

        return this.movieRepo.findAll();
    }

    public void saveMovies(Movie[] movies) {
        for (Movie m : movies) {
            if (!movieRepo.findById(m.getId()).isPresent()) {
                movieRepo.save(m);
                for (int genreId : m.getGenres()) {
                    movieGenreRepo.save(new MovieGenrePair(m.getId(), genreId));
                }
            }
        }
    }

    public void addRating(Rating rating, String username) throws InvalidRatingException {
        int id = rating.getMovieId();
        int rate = rating.getRating();
        if (rate < 1 || rate > 5) {
            throw new InvalidRatingException("Ratings must be between 1 and 5");
        }

        User user = userRepo.getOne(username);
        Movie movie = movieRepo.getOne(id);
        for (Rating r : user.getRatings()) {
            if (r.getMovieId() == id) {
                r.setRating(rate);
                ratingRepo.save(r);
                recalculateAverage(id);
                return;
            }
        }
        movie.addNewRate(rate);
        movieRepo.save(movie);
        rating.setUser(user);
        ratingRepo.save(rating);
    }

    private void recalculateAverage(int id) {
        double sum = 0;
        int count = 0;
        for (Rating r : ratingRepo.findAll()) {
            if (r.getMovieId() == id) {
                sum += r.getRating();
                count++;
            }
        }

        sum = sum / count;
        Movie movie = movieRepo.getOne(id);
        movie.setNumberOfVotes(count);
        movie.setRate(sum);
        movieRepo.save(movie);
    }

    public List<Genre> getGenres() throws IOException {
        RestTemplate rt = new RestTemplate();
        String url = API_URL + GENRES_PATH + KEY_PREFIX + API_KEY;
        ResponseEntity<String> response = rt.getForEntity(url, String.class);
        JsonNode root = mapper().readTree(response.getBody());
        JsonNode genres = root.path("genres");
        Genre[] genresObjects = mapper().treeToValue(genres, Genre[].class);
        saveGenres(genresObjects);

        return this.genreRepo.findAll();
    }

    private void saveGenres(Genre[] genresObjects) {
        for (Genre g : genresObjects) {
            this.genreRepo.save(g);
        }
    }

    public List<Movie> moviesOfGenre(int id){
        return genreRepo.getOne(id).getMovies();
    }

}
