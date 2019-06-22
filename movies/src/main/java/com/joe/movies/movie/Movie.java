package com.joe.movies.movie;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name="id")
    private int id;

    @Column(name = "id",nullable = false)
    private String title;

    @Column(name = "rate")
    private double rate;

    @Column(name="number_of_votes")
    private int numberOfVotes;

    @Column(name="release_date")
    private Date releaseDate;

    public Movie() {
    }

    public Movie(int id, String title, double rate, int numberOfVotes, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.numberOfVotes = numberOfVotes;
        this.releaseDate = releaseDate;
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
