package com.quiltplayer.core.repo;

import java.util.List;

import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;

/**
 * Album repo.
 * 
 * @author Vlado Palczynski
 */
public interface AlbumRepository
{
	List<Artist> search(String albumName);

	Album get(String albumId);
}
