package com.dm.favorites.integration.itunes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ItunesAlbum {

    public String collectionName;
    public String releaseDate;
}
