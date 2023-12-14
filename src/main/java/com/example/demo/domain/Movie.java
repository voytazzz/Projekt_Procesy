package com.example.demo.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String director;
    private int year;
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;
}
