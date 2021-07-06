package com.dm.favorites.domain.endpoints.favorite;

import com.dm.favorites.domain.artists.FavoriteArtist;
import com.dm.favorites.domain.artists.FavoriteArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteArtistController {

    private final FavoriteArtistService favoriteArtistService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public FavoriteArtist addFavoriteArtist(@RequestBody AddFavoriteArtistsRequest request) {
        return favoriteArtistService.addFavoriteArtist(request);
    }
}
