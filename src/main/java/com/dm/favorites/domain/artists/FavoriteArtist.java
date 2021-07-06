package com.dm.favorites.domain.artists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("FavoriteArtist")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FavoriteArtist {

    @Id
    private String userId;
    private String artistId;

}