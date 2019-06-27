package com.joe.movies.movie;

import javax.persistence.*;

@Entity
@Table(name = "movie_genre")
public class MovieGenrePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "movie_id")
    private int movieId;

    @Column(name="genre_id")
    private int genreId;

    public MovieGenrePair(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public MovieGenrePair() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
