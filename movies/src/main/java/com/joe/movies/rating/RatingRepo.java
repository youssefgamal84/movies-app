package com.joe.movies.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Integer> {
}
