package com.example.demo.repository;
import com.example.demo.domain.Movie;
import org.springframework.data.repository.CrudRepository;
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
