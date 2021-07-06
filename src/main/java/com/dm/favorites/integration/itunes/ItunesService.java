package com.dm.favorites.integration.itunes;

import com.dm.favorites.domain.endpoints.artist.Album;
import com.dm.favorites.domain.endpoints.artist.AlbumSearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItunesService {

    private final ItunesClient itunesClient;
    private final ObjectMapper objectMapper;

    public ArtistSearchResponse searchArtist(String artistName) {
        var artists = itunesClient.search("allArtist", artistName);
        return parseResponse(artists, ArtistSearchResponse.class);
    }

    public AlbumSearchResponse searchTopAlbums(String artistId) {
        var artists = itunesClient.lookup("album", artistId, 5);
        var itunesAlbumSearchResponse = parseResponse(artists, ItunesAlbumSearchResponse.class);
        List<Album> albums = itunesAlbumSearchResponse.results.stream()
                .filter(item -> Objects.nonNull(item.collectionName))
                .map(this::toAlbum)
                .collect(toList());
        return new AlbumSearchResponse(albums);
    }

    private Album toAlbum(ItunesAlbum item) {
        return Album.builder().collectionName(item.collectionName).releaseDate(item.releaseDate)
                .build();
    }

    //response needs to be parsed from string because itunes api doesnt respect "Accept" header and always returns text
    private <T> T parseResponse(String artists, Class<T> clazz) {
        try {
            System.out.println(artists);
            return objectMapper.readerFor(clazz).readValue(artists);
        } catch (JsonProcessingException e) {
            log.info("unable to parse itunes response ", e);
            log.debug("Itunes response {}", artists);
            throw new RuntimeException("Internal service error");
        }
    }


}
