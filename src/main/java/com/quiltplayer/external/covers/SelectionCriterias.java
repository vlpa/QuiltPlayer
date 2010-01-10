package com.quiltplayer.external.covers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import com.quiltplayer.external.covers.discogs.ArtistRelease;
import com.quiltplayer.external.covers.discogs.Resp;

public class SelectionCriterias extends CommonWebserviceMethods
{

	private static Logger log = Logger.getLogger(SelectionCriterias.class);

	public Resp startSelection(final Resp resp, final String albumTitle)
	{
		return searchReleaseByAlbumTitle(albumTitle, resp);
	}

	private Resp searchReleaseByAlbumTitle(final String albumTitle, Resp resp)
	{
		boolean foundRelease = false;

		for (ArtistRelease release : resp.getArtist().getReleases())
		{
			if (release.getTitle().equalsIgnoreCase(albumTitle))
			{
				foundRelease = true;

				break;
			}
		}

		if (!foundRelease)
		{
			resp = checkOtherArtistsWithSameName(resp.getArtist().getName(),
					albumTitle);
		}

		return resp;
	}

	/**
	 * Check other artist with same name. Could be an Discogs naming issue if
	 * the album was not found.
	 * 
	 * @param artist
	 *            the artist to set.
	 * @param album
	 *            the album to set.
	 * @return Resp
	 */
	private Resp checkOtherArtistsWithSameName(final String artistName,
			final String albumTitle)
	{
		int i = 2; // Discogs starts with (2)

		// An NotFoundException will occure or the correct artist will be found
		while (true)
		{
			try
			{
				URL url = new URL("http://www.discogs.com/artist/" + artistName
						+ "+(" + i + ")" + "?f=xml&api_key=" + DISCOGS_API_KEY);

				log.debug("Webservice call for artist: " + url.toString());

				InputStream stream = getStreamResponse(url);

				Resp resp = parser.parseArtist(stream);

				stream.close();

				for (ArtistRelease release : resp.getArtist().getReleases())
				{
					if (release.getTitle().equalsIgnoreCase(albumTitle))
					{
						log.debug("Found artist for Album: "
								+ resp.getArtist().getName());

						return resp;
					}
				}
				i++;
			}
			catch (Exception e)
			{
				if (e instanceof FileNotFoundException)
				{
					log.info("No artist found that matched the album...");

					return null;
				}
				else if (e instanceof IOException)
				{
					log.error(e.getMessage());

					if (e.getMessage().contains("400"))
						Thread.currentThread().stop();

					return null;
				}
			}
		}
	}
}