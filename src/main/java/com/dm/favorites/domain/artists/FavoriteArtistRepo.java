package com.dm.favorites.domain.artists;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteArtistRepo extends CrudRepository<FavoriteArtist, String> {
}