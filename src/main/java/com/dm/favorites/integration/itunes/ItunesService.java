package com.dm.favorites.integration.itunes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItunesService {

    private final ItunesClient itunesClient;
    private final ObjectMapper objectMapper;

    public ArtistSearchResponse searchArtist(String artistName) {
        var artists = itunesClient.search("allArtist", artistName);
        return parseResponse(artists);
    }

    //response needs to be parsed from string because itunes api doesnt respect "Accept" header and always returns text
    private ArtistSearchResponse parseResponse(String artists) {
        try {
            return objectMapper.readerFor(ArtistSearchResponse.class).readValue(artists);
        } catch (JsonProcessingException e) {
            log.info("unable to parse itunes response ", e);
            log.debug("Itunes response {}", artists);
            throw new RuntimeException("Internal service error");
        }
    }


}
