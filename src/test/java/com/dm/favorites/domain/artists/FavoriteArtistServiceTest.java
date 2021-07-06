package com.dm.favorites.domain.artists;


import com.dm.favorites.domain.endpoints.favorite.AddFavoriteArtistsRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class FavoriteArtistServiceTest {

    FavoriteArtistRepo favoriteArtistRepo = mock(FavoriteArtistRepo.class);

    FavoriteArtistService favoriteArtistService = new FavoriteArtistService(favoriteArtistRepo);

    @Test
    void givenArtistIdIsNotNumericThenExceptionThrown() {
        AddFavoriteArtistsRequest request = new AddFavoriteArtistsRequest("user", "artist");
        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> favoriteArtistService.addFavoriteArtist(request));

        assertEquals("artistId must be numeric value", exception.getMessage());
    }

    @Test
    void givenArtistIdIsNotNullThenExceptionThrown() {
        AddFavoriteArtistsRequest request = new AddFavoriteArtistsRequest("user", null);
        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> favoriteArtistService.addFavoriteArtist(request));

        assertEquals("UserId and Artist cannot be empty", exception.getMessage());
    }

    @Test
    void givenUserIdIsNotNullThenExceptionThrown() {
        AddFavoriteArtistsRequest request = new AddFavoriteArtistsRequest(null, "artist");
        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> favoriteArtistService.addFavoriteArtist(request));

        assertEquals("UserId and Artist cannot be empty", exception.getMessage());
    }
}
