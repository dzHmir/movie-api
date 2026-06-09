package com.movieapi.repository;

import com.movieapi.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenres_Id(Long genreId);
    List<Movie> findByActors_Id(Long actorId);
    Page<Movie> findByGenres_Id(Long genreId, Pageable pageable);
    Page<Movie> findByActors_Id(Long actorId, Pageable pageable);
}