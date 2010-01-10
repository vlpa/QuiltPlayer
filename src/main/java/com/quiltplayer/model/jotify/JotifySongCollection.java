package com.quiltplayer.model.jotify;

import java.util.List;

import com.quiltplayer.model.Song;
import com.quiltplayer.model.SongCollection;

public class JotifySongCollection implements SongCollection
{
	private List<Song> songs;

	@Override
	public List<Song> getSongs()
	{
		return songs;
	}

	@Override
	public void setSongs(List<Song> songs)
	{
		this.songs = songs;
	}

}
