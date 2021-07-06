package com.dm.favorites.acceptance;

import com.dm.favorites.WireMockInitializer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
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
    public void whenArtistSearchThenReturn200AndMinimizedBody() throws Exception {
        mockAbbaSearch();

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/artists")
                .param("name", "abba")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$.results[0].artistName", is("ABBA")));
        result.andExpect(jsonPath("$.results[0].artistId", is("372976")));
    }

    @Test
    public void whenItunesReturnsThenReturn200AndMinimizedBody() throws Exception {
        mockAlbumSearch();

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/artists/372976/albums")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$.results[0].collectionName", is("Do What Thou Wilt")));
        result.andExpect(jsonPath("$.results[0].releaseDate", is("2016-12-09T08:00:00Z")));
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

    private void mockAlbumSearch() {
        this.wireMockServer.stubFor(
                WireMock.get("/itunes/lookup?entity=album&id=372976&limit=5")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\n" +
                                        " \"resultCount\":6,\n" +
                                        " \"results\": [\n" +
                                        "{\"wrapperType\":\"artist\", \"artistType\":\"Artist\", \"artistName\":\"Ab-Soul\", \"artistLinkUrl\":\"https://music.apple.com/us/artist/ab-soul/392386318?uo=4\", \"artistId\":392386318, \"amgArtistId\":1168200, \"primaryGenreName\":\"Hip-Hop/Rap\", \"primaryGenreId\":18}, \n" +
                                        "{\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":392386318, \"collectionId\":1182583589, \"amgArtistId\":1168200, \"artistName\":\"Ab-Soul\", \"collectionName\":\"Do What Thou Wilt\", \"collectionCensoredName\":\"Do What Thou Wilt\", \"artistViewUrl\":\"https://music.apple.com/us/artist/ab-soul/392386318?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/do-what-thou-wilt/1182583589?uo=4\", \"artworkUrl60\":\"https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/c3/11/d9/c311d97a-f7ba-3257-30bc-d8414c541526/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/c3/11/d9/c311d97a-f7ba-3257-30bc-d8414c541526/source/100x100bb.jpg\", \"collectionPrice\":10.99, \"collectionExplicitness\":\"explicit\", \"contentAdvisoryRating\":\"Explicit\", \"trackCount\":16, \"copyright\":\"℗ 2016 Top Dawg Entertainment\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2016-12-09T08:00:00Z\", \"primaryGenreName\":\"Hip-Hop/Rap\"}, \n" +
                                        "{\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":392386318, \"collectionId\":918491537, \"amgArtistId\":1168200, \"artistName\":\"Ab-Soul\", \"collectionName\":\"These Days...\", \"collectionCensoredName\":\"These Days...\", \"artistViewUrl\":\"https://music.apple.com/us/artist/ab-soul/392386318?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/these-days/918491537?uo=4\", \"artworkUrl60\":\"https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/98/0a/de/980ade12-7552-cfce-5c93-760fadd47101/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/98/0a/de/980ade12-7552-cfce-5c93-760fadd47101/source/100x100bb.jpg\", \"collectionPrice\":10.99, \"collectionExplicitness\":\"explicit\", \"contentAdvisoryRating\":\"Explicit\", \"trackCount\":15, \"copyright\":\"℗ 2014 Top Dawg Entertainment\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2014-06-24T07:00:00Z\", \"primaryGenreName\":\"Hip-Hop/Rap\"}, \n" +
                                        "{\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":392386318, \"collectionId\":525933456, \"amgArtistId\":1168200, \"artistName\":\"Ab-Soul\", \"collectionName\":\"Control System\", \"collectionCensoredName\":\"Control System\", \"artistViewUrl\":\"https://music.apple.com/us/artist/ab-soul/392386318?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/control-system/525933456?uo=4\", \"artworkUrl60\":\"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/91/24/2d/91242d48-6f31-7f82-7821-80cda106f063/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/91/24/2d/91242d48-6f31-7f82-7821-80cda106f063/source/100x100bb.jpg\", \"collectionPrice\":9.99, \"collectionExplicitness\":\"explicit\", \"contentAdvisoryRating\":\"Explicit\", \"trackCount\":17, \"copyright\":\"℗ 2012 TopDawg Ent.\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2012-05-11T07:00:00Z\", \"primaryGenreName\":\"Hip-Hop/Rap\"}, \n" +
                                        "{\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":392386318, \"collectionId\":428131719, \"amgArtistId\":1168200, \"artistName\":\"Ab-Soul\", \"collectionName\":\"Longterm Mentality\", \"collectionCensoredName\":\"Longterm Mentality\", \"artistViewUrl\":\"https://music.apple.com/us/artist/ab-soul/392386318?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/longterm-mentality/428131719?uo=4\", \"artworkUrl60\":\"https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/c2/d2/b9/c2d2b970-5359-c06b-9e37-078683b39de4/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/c2/d2/b9/c2d2b970-5359-c06b-9e37-078683b39de4/source/100x100bb.jpg\", \"collectionPrice\":9.99, \"collectionExplicitness\":\"explicit\", \"contentAdvisoryRating\":\"Explicit\", \"trackCount\":14, \"copyright\":\"℗ 2011 Top Dawg Entertainment\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2011-04-05T07:00:00Z\", \"primaryGenreName\":\"Hip-Hop/Rap\"}, \n" +
                                        "{\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":920961092, \"collectionId\":1442946592, \"artistName\":\"Daylyt, UTK, Ab-Soul & Loaded Lux\", \"collectionName\":\"Talk to Em 2 - Single\", \"collectionCensoredName\":\"Talk to Em 2 - Single\", \"artistViewUrl\":\"https://music.apple.com/us/artist/daylyt/920961092?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/talk-to-em-2-single/1442946592?uo=4\", \"artworkUrl60\":\"https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/46/53/8b/46538bd3-99df-e8ac-2f10-0f0ffcb1f9a3/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/46/53/8b/46538bd3-99df-e8ac-2f10-0f0ffcb1f9a3/source/100x100bb.jpg\", \"collectionPrice\":0.99, \"collectionExplicitness\":\"explicit\", \"contentAdvisoryRating\":\"Explicit\", \"trackCount\":1, \"copyright\":\"℗ 2018 FGTBD\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2018-11-06T08:00:00Z\", \"primaryGenreName\":\"Hip-Hop/Rap\"}]\n" +
                                        "}"))
        );
    }
}
