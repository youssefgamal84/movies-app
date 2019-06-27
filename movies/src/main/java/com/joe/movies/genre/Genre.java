package com.joe.movies.genre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.movies.movie.Movie;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genre {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genreObjects")
    @JsonIgnore
    private List<Movie> movies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
