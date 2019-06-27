package com.joe.movies.rating;

import com.joe.movies.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "date_of_vote")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    public Rating(int movieId, int rating) {
        this.movieId = movieId;
        this.rating = rating;
        this.timestamp = new Date();
    }

    public Rating() {
        this.timestamp = new Date();
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
