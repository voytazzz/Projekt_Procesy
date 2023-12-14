package com.example.demo.service;

import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return this.movieRepository.findById(id);
    }

    public Movie addMovie(Movie movie) {
        return this.movieRepository.save(movie);
    }

    public ResponseEntity<Movie> updateMovie(Long id, Movie updatedMovie) {
        Optional<Movie> existingMovieOptional = this.movieRepository.findById(id);
        if (existingMovieOptional.isPresent()) {
            Movie existingMovie = existingMovieOptional.get();
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setDirector(updatedMovie.getDirector());
            existingMovie.setYear(updatedMovie.getYear());
            Movie savedMovie = this.movieRepository.save(existingMovie);
            return ResponseEntity.ok(savedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteMovie(Long id) {
        this.movieRepository.deleteById(id);
    }
}
