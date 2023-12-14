package com.example.demo;

import com.example.demo.domain.Client;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Review;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllReviews() throws Exception {
        Client testClient = new Client();
        testClient.setFirstName("Test");
        testClient.setLastName("Client");
        testClient.setEmail("test.client@example.com");
        clientRepository.save(testClient);

        Movie testMovie = new Movie();
        testMovie.setTitle("Test Movie");
        testMovie.setDirector("Test Director");
        testMovie.setYear(2022);
        movieRepository.save(testMovie);


        Review testReview = new Review();
        testReview.setDate(LocalDate.now());
        testReview.setReview("Test Review");
        testReview.setDescription("Test Description");
        testReview.setClient(testClient);
        testReview.setMovie(testMovie);
        reviewRepository.save(testReview);

        mockMvc.perform(MockMvcRequestBuilders.get("/reviews"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testReview.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review").value(testReview.getReview()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(testReview.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client.id").value(testClient.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movie.id").value(testMovie.getId()));
    }



    @Test
    public void addReview() throws Exception {
        Client testClient = new Client();
        testClient.setFirstName("Test");
        testClient.setLastName("Client");
        testClient.setEmail("test.client@example.com");
        clientRepository.save(testClient);


        Movie testMovie = new Movie();
        testMovie.setTitle("Test Movie");
        testMovie.setDirector("Test Director");
        testMovie.setYear(2022);
        movieRepository.save(testMovie);

        Review newReview = new Review();
        newReview.setDate(LocalDate.now());
        newReview.setReview("New Review");
        newReview.setDescription("New Description");

        newReview.setClient(testClient);
        newReview.setMovie(testMovie);

        String jsonBody = objectMapper.writeValueAsString(newReview);

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.review").value(newReview.getReview()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(newReview.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").value(testClient.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movie.id").value(testMovie.getId()));
    }

}
