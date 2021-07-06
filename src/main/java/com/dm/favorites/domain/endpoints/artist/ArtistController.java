package com.dm.favorites.domain.endpoints.artist;

import com.dm.favorites.integration.itunes.ArtistSearchResponse;
import com.dm.favorites.integration.itunes.ItunesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ItunesService itunesService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ArtistSearchResponse searchArtist(@RequestParam("name") String name) {
        return itunesService.searchArtist(name);
    }
}
