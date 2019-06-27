package com.joe.movies.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.joe.movies.genre.Genre;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class Movie {

    @Id
    @Column(name="id")
    private int id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "rate")
    private double rate;

    @Column(name="number_of_votes")
    private int numberOfVotes;

    @Column(name="release_date")
    private Date releaseDate;

    @Transient
    private List<Integer> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_genre",
    joinColumns = {@JoinColumn(name="movie_id")},
    inverseJoinColumns = {@JoinColumn(name="genre_id")})
    private List<Genre> genreObjects;


    public Movie() {
        this.genres=new ArrayList<>();
    }

    public Movie(int id, String title, double rate, int numberOfVotes, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.numberOfVotes = numberOfVotes;
        this.releaseDate = releaseDate;
        this.genres=new ArrayList<>();
    }

    @JsonIgnore
    public List<Integer> getGenres() {
        return genres;
    }

    @JsonSetter(value = "genre_ids")
    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenreObjects() {
        return genreObjects;
    }

    public void setGenreObjects(List<Genre> genreObjects) {
        this.genreObjects = genreObjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    @JsonSetter(value = "release_date")
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void addNewRate(int rate){
        this.rate = ((this.numberOfVotes*this.rate)+rate)/(++this.numberOfVotes);
    }
}
