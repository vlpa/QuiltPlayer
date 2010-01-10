package com.quiltplayer.core.storage;

import java.util.Collection;

import com.quiltplayer.model.Artist;
import com.quiltplayer.model.StringId;

/**
 * Interface for storing the data in the player.
 * 
 * @author Vlado Palczynski
 */
public interface ArtistStorage
{
	/**
	 * Create an artist with id.
	 * 
	 * @param id
	 *            the id to set.
	 * @return Artist
	 */
	Artist createArtist(StringId id);

	/**
	 * Return artist.
	 * 
	 * @param artistId
	 *            the artistId to search withs.
	 * @return found Artist or null.
	 */
	Artist getArtist(StringId artistId);

	/**
	 * Get all artists,
	 * 
	 * @return All artist or empty collection if none.
	 */
	Collection<Artist> getArtists();

	/**
	 * Delete artist.
	 * 
	 * @param artist
	 *            the artist to delete.
	 */
	void delete(Artist artist);

	void deleteIfNoAlbumRelations(Artist artist);

	int countArtists();

}
