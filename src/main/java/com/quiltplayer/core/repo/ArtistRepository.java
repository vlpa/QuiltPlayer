package com.quiltplayer.core.repo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.quiltplayer.model.Artist;

/**
 * 
 * 
 * @author Vlado Palczynski
 */
public interface ArtistRepository
{
	List<Artist> getArtists();

	List<Artist> search(String artistName);

	Map<String, List<Artist>> getArtistsByChars();

	void rebuildArtistCache(Collection<Artist> artists);
}
