package com.dm.favorites.domain.endpoints.artist;

import com.dm.favorites.domain.endpoints.artist.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumSearchResponse {

    public List<Album> results;
}
