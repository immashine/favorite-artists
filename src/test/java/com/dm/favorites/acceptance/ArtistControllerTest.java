package com.dm.favorites.acceptance;


import com.dm.favorites.FavoritesApplication;
import com.dm.favorites.WireMockInitializer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import static com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.WireMockHelpers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class ArtistControllerTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    public void afterEach() {
        wireMockServer.resetAll();
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
        mockAbbaSearch();

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/artists")
                .param("name", "abba")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$.results[0].artistName", is("ABBA")));
        result.andExpect(jsonPath("$.results[0].artistId", is("372976")));
    }

    private void mockAbbaSearch() {
        this.wireMockServer.stubFor(
                WireMock.get("/itunes/search?entity=allArtist&term=abba")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\n" +
                                        "  \"resultCount\": 2,\n" +
                                        "  \"results\": [\n" +
                                        "    {\n" +
                                        "      \"wrapperType\": \"artist\",\n" +
                                        "      \"artistType\": \"Artist\",\n" +
                                        "      \"artistName\": \"ABBA\",\n" +
                                        "      \"artistLinkUrl\": \"https://music.apple.com/us/artist/abba/372976?uo=4\",\n" +
                                        "      \"artistId\": 372976,\n" +
                                        "      \"amgArtistId\": 3492,\n" +
                                        "      \"primaryGenreName\": \"Pop\",\n" +
                                        "      \"primaryGenreId\": 14\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "      \"wrapperType\": \"artist\",\n" +
                                        "      \"artistType\": \"Artist\",\n" +
                                        "      \"artistName\": \"ABBA-DJ\",\n" +
                                        "      \"artistLinkUrl\": \"https://music.apple.com/us/artist/abba-dj/105311823?uo=4\",\n" +
                                        "      \"artistId\": 105311823,\n" +
                                        "      \"amgArtistId\": 612090,\n" +
                                        "      \"primaryGenreName\": \"Dance\",\n" +
                                        "      \"primaryGenreId\": 17\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}"))
        );
    }
}
