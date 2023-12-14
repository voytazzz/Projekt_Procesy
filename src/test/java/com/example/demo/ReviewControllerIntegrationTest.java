package com.example.demo;

import com.example.demo.domain.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllReviews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getReviewById() throws Exception {
        // Assuming there is at least one review in the database with id = 1
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addReview() throws Exception {
        Review newReview = new Review();
        newReview.setDate(LocalDate.now());
        newReview.setReview("Sample Review");
        newReview.setDescription("Sample Description");

        String jsonBody = objectMapper.writeValueAsString(newReview);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Review addedReview = objectMapper.readValue(responseBody, Review.class);

        // Assuming the added review has been assigned an id
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/" + addedReview.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
