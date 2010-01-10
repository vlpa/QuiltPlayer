package com.quiltplayer.core.storage;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.StringId;

/**
 * Interface for storing the data in the player.
 * 
 * @author Vlado Palczynski
 */
public interface Storage
{
	/**
	 * Create album.
	 * 
	 * @param albumId
	 *            the albumId to set.
	 * @return Album
	 */
	Album createAlbum(StringId albumId);

	/**
	 * Create Song.
	 * 
	 * @param songId
	 *            the songId to set.
	 * @return Song
	 */
	Song createSong(StringId songId);

	/**
	 * Return album by albumId.
	 * 
	 * @param albumId
	 *            the albumId to search with.
	 * @return found Album or null.
	 */
	Album getAlbum(StringId albumId);

	/**
	 * Return song by songId.
	 * 
	 * @param albumTitle
	 *            the albumTitle the song exists in.
	 * @param songId
	 *            the songId to search with.
	 * @return found Song or null.
	 */
	Song getSong(String albumTitle, StringId songId);

	/**
	 * Get all albums.
	 * 
	 * @return All albums or empty collection if none found.
	 */
	List<Album> getAlbums(Collection<Artist> artists);

	/**
	 * Get all albums.
	 * 
	 * @return All albums or empty stack if none found.
	 */
	Stack<Album> getAlbumsAsStack(Collection<Artist> artists);

	/**
	 * Delete album.
	 * 
	 * @param album
	 *            the album to delete.
	 */
	void delete(Album album);

	/**
	 * Create local image.
	 * 
	 * @param album
	 *            the album to add the image to.
	 * @param path
	 *            the path of the image.
	 * @param type
	 *            the type of image.
	 * @return Created image or null.
	 */
	LocalImage createLocalImage(Album album, String fileName, LocalImage image);

	/**
	 * Retrieves local image by path.
	 * 
	 * @param path
	 *            the path to search with.
	 * @return LocalImage or null.
	 */
	LocalImage getLocalImage(String path);

}
