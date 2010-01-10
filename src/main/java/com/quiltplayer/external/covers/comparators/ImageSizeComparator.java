package com.quiltplayer.external.covers.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.quiltplayer.external.covers.discogs.AlbumRelease;

/**
 * Compare by year.
 * 
 * @author Vlado Palczynski
 */
public class ImageSizeComparator implements Serializable,
		Comparator<AlbumRelease>
{
	private static final long serialVersionUID = -92024627194936852L;

	/*
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public final int compare(final AlbumRelease o1, final AlbumRelease o2)
	{
		if (o1.getImages() == null)
		{
			return 1;
		}
		else if (o2.getImages() == null)
		{
			return -1;
		}

		return o2.getImages().size() - o1.getImages().size();
	}
}
