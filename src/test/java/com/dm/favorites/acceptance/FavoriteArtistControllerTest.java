package com.dm.favorites.acceptance;

import com.dm.favorites.domain.artists.FavoriteArtist;
import com.dm.favorites.domain.artists.FavoriteArtistRepo;
import com.dm.favorites.domain.endpoints.favorite.AddFavoriteArtistsRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteArtistControllerTest extends AcceptanceTestBase{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FavoriteArtistRepo favoriteArtistRepo;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenArtistSavedSuccessfullyThenReturn200() throws Exception {
        var request = new AddFavoriteArtistsRequest("1522", "556158");

        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/favorites")
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$.userId", is("1522")));
        result.andExpect(jsonPath("$.artistId", is("556158")));

        Optional<FavoriteArtist> byId = favoriteArtistRepo.findById("1522");
        assertTrue(byId.isPresent());
        assertEquals("1522", byId.get().getUserId());
        assertEquals("556158", byId.get().getArtistId());
    }

}
