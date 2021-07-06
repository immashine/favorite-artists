package com.dm.favorites.domain.endpoints.favorite;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddFavoriteArtistsRequest {

    public String userId;
    public String artistId;

}