package com.quiltplayer.external.covers.model;

import java.io.File;

/**
 * Image object containing information about a local image.
 * 
 * @author Vlado Palczynski
 */
public interface LocalImage {
	/**
	 * Image type primary.
	 */
	String TYPE_PRIMARY = "primary";

	/**
	 * Image type secondary.
	 */
	String TYPE_SECONDARY = "secondary";

	/**
	 * @return the height
	 */
	String getHeight();

	/**
	 * @param height
	 *            the height to set
	 */
	void setHeight(String height);

	/**
	 * @return the type
	 */
	String getType();

	/**
	 * @param type
	 *            the type to set
	 */
	void setType(String type);

	/**
	 * @return the large image.
	 */
	File getLargeImage();

	/**
	 * @param largeImage
	 *            the large image to set.
	 */
	void setLargeImage(File largeImage);

	/**
	 * @return the small image.
	 */
	File getSmallImage();

	/**
	 * @param smallImage
	 *            The small image to set.
	 */
	void setSmallImage(File smallImage);

	/**
	 * @return the medium image.
	 */
	File getMediumImage();

	/**
	 * @param mediumImage
	 *            the medium image to set.
	 */
	void setMediumImage(File mediumImage);

	/**
	 * @return the width
	 */
	String getWidth();

	/**
	 * @param width
	 *            the width to set
	 */
	void setWidth(String width);
}
