package com.dm.favorites.domain.artists;

import com.dm.favorites.domain.endpoints.favorite.AddFavoriteArtistsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
public class FavoriteArtistService {

    private final FavoriteArtistRepo favoriteArtistRepo;

    public FavoriteArtist addFavoriteArtist(AddFavoriteArtistsRequest request) {
        validate(request);
        var favoriteArtist = FavoriteArtist.builder()
                .artistId(request.artistId)
                .userId(request.userId)
                .build();
        return favoriteArtistRepo.save(favoriteArtist);
    }

    private void validate(AddFavoriteArtistsRequest request) {
        //would need to check for userId validity but format was not specified
        if (isNull(request.artistId) || isNull(request.userId)) {
            throw new IllegalArgumentException("UserId and Artist cannot be empty");
        }
        if (!isNumeric(request.artistId)) {
            throw new IllegalArgumentException("artistId must be numeric value");
        }
    }

    private static boolean isNumeric(String strNum) {
        if (isNull(strNum)) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}