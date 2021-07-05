package com.dm.favorites.integration.itunes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ItunesArtist {

    public String artistName;
    public String artistId;
}
